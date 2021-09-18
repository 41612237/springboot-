package com.tomgao.miaoshabaisc.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tomgao
 * @date 2021/9/4 8:04 下午
 */
@Data
public class PromoModel {

    private Integer id;

    /**
     * 秒杀活动状态 1 表示未开始, 2 表示进行中, 3表示已结束
     */
    private Integer status;

    /**
     * 秒杀活动名称
     */
    private String promoName;

    /**
     * 秒杀活动开始时间
     */
    private DateTime startDate;

    /**
     * 秒杀活动结束时间
     */
    private DateTime endDate;

    /**
     * 秒杀活动适用商品
     */
    private Integer item;

    /**
     * 秒杀活动商品价格
     */
    private BigDecimal promoItemPrice;
}
