package com.tomgao.miaoshabaisc.service.impl;

import com.tomgao.miaoshabaisc.dao.OrderDOMapper;
import com.tomgao.miaoshabaisc.dao.SequenceDOMapper;
import com.tomgao.miaoshabaisc.dataobject.OrderDO;
import com.tomgao.miaoshabaisc.dataobject.SequenceDO;
import com.tomgao.miaoshabaisc.error.BusinessException;
import com.tomgao.miaoshabaisc.error.EmBusinessError;
import com.tomgao.miaoshabaisc.service.ItemService;
import com.tomgao.miaoshabaisc.service.OrderService;
import com.tomgao.miaoshabaisc.service.UserService;
import com.tomgao.miaoshabaisc.service.model.ItemModel;
import com.tomgao.miaoshabaisc.service.model.OrderModel;
import com.tomgao.miaoshabaisc.service.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author tomgao
 * @date 2021/9/4 3:52 下午
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId,Integer promoId, Integer amount) throws BusinessException {

        // 1.校验下单状态 商品是否存在, 用户是否合法, 购买数量是否正确 是否有活动
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");
        }

//        UserModel userModel = userService.getUserById(userId);
//        if (userModel == null) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");
//        }

        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品数量不正确");
        }

        if (promoId != null) {
            // 1. 校验对应活动是否存在这个使用商品
            if (promoId != itemModel.getPromoModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
                // 2. 检验活动是否正在进行中
            } else if (itemModel.getPromoModel().getStatus() != 2){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动还未开始");
            }
        }

        // 2.落单减库存 (不要支付减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        // 3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setItemId(itemId);
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);

        if (promoId != null) {
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else {
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(BigDecimal.valueOf(amount)));

        // 生成交易流水号 订单号
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        // 增加商品销量
        itemService.increaseSales(itemId, amount);
        // 4.返回
        return orderModel;
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        // BigDecimal不能转 double
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }

    // 生成自增 id
    @Transactional(propagation = Propagation.REQUIRES_NEW) // 开启一个新的事务, 嵌套在其他事务中, 保证他自己执行完成就可以
    String generateOrderNo() {
        // 订单号有 16 位
        StringBuilder stringBuilder = new StringBuilder();
        // 前 8 位位时间信息, 年月日
        LocalDateTime now = LocalDateTime.now();
        // 2021-09-01
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);
        // 中间 6 位为自增序列
        // 获取当前 sequence
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        // sequence自增
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        // 凑足 6 位
        String s = String.valueOf(sequence);
        for (int i = 0; i < 6 - s.length(); i++) {
            stringBuilder.append("0");
        }

        stringBuilder.append(s);

        // 最后 2 位为分库分表位 暂时写死
        stringBuilder.append("00");
        return stringBuilder.toString();
    }
}
