package com.tomgao.miaoshabaisc.controller;

import com.tomgao.miaoshabaisc.error.BusinessException;
import com.tomgao.miaoshabaisc.error.EmBusinessError;
import com.tomgao.miaoshabaisc.response.CommonReturnType;
import com.tomgao.miaoshabaisc.service.OrderService;
import com.tomgao.miaoshabaisc.service.model.OrderModel;
import com.tomgao.miaoshabaisc.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tomgao
 * @date 2021/9/4 6:59 下午
 */
@RestController
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class OrderController extends BaseController{

    @Autowired
    OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //封装下单请求
    @RequestMapping("/create")
    public CommonReturnType createOrder(Integer itemId,
                                        @RequestParam(value = "promoId", required = false) Integer promoId,
                                        Integer amount) throws BusinessException {

        // 校验用户是否登录
//        Boolean isLogin = (Boolean) httpServletRequest.getSession().getAttribute("IS_LOGIN");
//        if (isLogin == null || !isLogin) {
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户还未登录, 不能下单");
//        }
//        // 获取用户登录信息
//        UserModel userModel = (UserModel) httpServletRequest.getSession().getAttribute("LOGIN_USER");

        // 下单
        OrderModel order = orderService.createOrder(null, itemId,promoId, amount);
        return CommonReturnType.create(null);
    }

}
