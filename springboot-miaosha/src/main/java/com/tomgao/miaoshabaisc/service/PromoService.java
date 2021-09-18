package com.tomgao.miaoshabaisc.service;

import com.tomgao.miaoshabaisc.service.model.PromoModel;

/**
 * @author tomgao
 * @date 2021/9/4 9:27 下午
 */
public interface PromoService {

    // 根据 itemId 获取即将进行的或正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
}
