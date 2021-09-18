package com.tomgao.miaoshabaisc.service.model;

/**
 * @author tomgao
 * @date 2021/9/4 3:25 下午
 */

import lombok.Data;

import java.math.BigDecimal;

/**
 * 用户下单交易模型
 */
@Data
public class OrderModel {

    // 20181021000012828 时间
    private String id;

    private Integer userId;

    private Integer itemId;

    // 购买当时商品的单价 商品价格可能变化, 所以必须有这个字段 不能直接用 itemId 对应的商品价格
    // 若 promoId非空, 则表示秒杀商品价格
    private BigDecimal itemPrice;

    // 若费控, 则表示是以秒杀商品方式下单
    private Integer promoId;

    // 购买数量
    private Integer amount;

    // 购买金额
    // 若 promoId非空, 则表示秒杀商品价格
    private BigDecimal orderPrice;
}
