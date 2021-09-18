package com.tomgao.miaoshabaisc.controller.vo;

import lombok.Data;
import org.joda.time.DateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author tomgao
 * @date 2021/9/4 11:22 上午
 */
@Data
public class ItemVo {

    private Integer id;

    /**
     * 商品名称
     */
    private String title;

    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 商品图片
     */
    private String imgUrl;

    /**
     * 记录商品是否在秒杀活动中, 以及对应的状态  0: 表示没有秒杀活动 1:秒杀活动待开始 2:表示秒杀活动进行中
     */
    private Integer promoStatus;

    /**
     * 秒杀活动价格
     */
    private BigDecimal promoPrice;

    /**
     * 秒杀活动 id
     */
    private Integer promoId;

    /**
     * 秒杀活动开始时间
     */
    private String startTime;

}
