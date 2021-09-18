package com.tomgao.miaoshabaisc.service;

import com.tomgao.miaoshabaisc.error.BusinessException;
import com.tomgao.miaoshabaisc.service.model.ItemModel;

import java.util.List;

/**
 * @author tomgao
 * @date 2021/9/4 10:37 上午
 */
public interface ItemService {

    // 创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    // 商品列表浏览
    List<ItemModel> listItem();

    // 商品详情浏览
    ItemModel getItemById(Integer id);

    // 库存扣减
    boolean decreaseStock(Integer itemId, Integer amount);

    // 商品销量增加
    void increaseSales(Integer itemId, Integer amount);

}
