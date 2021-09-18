package com.tomgao.miaoshabaisc.service;

import com.tomgao.miaoshabaisc.error.BusinessException;
import com.tomgao.miaoshabaisc.service.model.OrderModel;

/**
 * @author tomgao
 * @date 2021/9/4 3:47 下午
 */
public interface OrderService {

    // 推荐1. 通过前端 url 上传过来秒杀活动 id,然后下单接口内校验对应 id 是否属于对应商品且活动已开始
    // 2. 直接在下单接口内判断对应商品是否存在秒杀活动, 若存在进行中的则以秒杀价格下单
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
}
