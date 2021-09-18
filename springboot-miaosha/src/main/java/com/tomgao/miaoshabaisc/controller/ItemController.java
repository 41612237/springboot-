package com.tomgao.miaoshabaisc.controller;

import com.tomgao.miaoshabaisc.controller.vo.ItemVo;
import com.tomgao.miaoshabaisc.error.BusinessException;
import com.tomgao.miaoshabaisc.response.CommonReturnType;
import com.tomgao.miaoshabaisc.service.ItemService;
import com.tomgao.miaoshabaisc.service.model.ItemModel;
import com.tomgao.miaoshabaisc.service.model.PromoModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.dc.pr.PRError;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tomgao
 * @date 2021/9/4 11:22 上午
 */

@RestController("item")
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;

    @RequestMapping("/create")
    public CommonReturnType createItem(String title,
                                       String description,
                                       BigDecimal price,
                                       Integer stock,
                                       String imgUrl
                                       ) throws BusinessException {

        // 封装 service 用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel item = itemService.createItem(itemModel);
        ItemVo itemVo = convertFromModel(item);
        return CommonReturnType.create(itemVo);
    }

    // 商品详情浏览
    @RequestMapping("/get")
    public CommonReturnType getItem(Integer id) {

        ItemModel itemModel = itemService.getItemById(id);
        ItemVo itemVo = convertFromModel(itemModel);
        return CommonReturnType.create(itemVo);
    }

    @RequestMapping("/list")
    public CommonReturnType listItem() {
        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVo> itemVoList = itemModelList.stream().map(this::convertFromModel).collect(Collectors.toList());
        return CommonReturnType.create(itemVoList);
    }

    private ItemVo convertFromModel(ItemModel itemModel) {

        ItemVo itemVo = new ItemVo();
        BeanUtils.copyProperties(itemModel, itemVo);
        if (itemModel.getPromoModel() != null) {
            // 有正在进行或吉江进行的秒杀活动
            itemVo.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVo.setPromoId(itemModel.getPromoModel().getId());
            itemVo.setStartTime(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVo.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        } else itemVo.setPromoStatus(0);

        return itemVo;
    }
}
