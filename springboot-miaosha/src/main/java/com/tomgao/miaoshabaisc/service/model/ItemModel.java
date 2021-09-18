package com.tomgao.miaoshabaisc.service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author tomgao
 * @date 2021/9/3 5:06 下午
 */

@Data
public class ItemModel {

    private Integer id;

    // 使用聚合模型, 如果 promoModel 不为空, 则表示有还未结束的秒杀活动
    private PromoModel promoModel;
    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格必须大于 0")
    private BigDecimal price;

    /**
     * 库存
     */
    @NotNull(message = "库存不能为空")
    private Integer stock;

    /**
     * 商品描述
     */
    @NotBlank(message = "商品描述信息不能为空")
    private String description;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 商品图片
     */
    @NotBlank(message = "商品图片信息不能为空")
    private String imgUrl;

}
