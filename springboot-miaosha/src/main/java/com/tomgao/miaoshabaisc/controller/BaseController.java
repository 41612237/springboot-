package com.tomgao.miaoshabaisc.controller;

import com.tomgao.miaoshabaisc.error.BusinessException;
import com.tomgao.miaoshabaisc.error.CommonError;
import com.tomgao.miaoshabaisc.error.EmBusinessError;
import com.tomgao.miaoshabaisc.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tomgao
 * @date 2021/8/18 10:04 下午
 */


public class BaseController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody // 直接返回对象json
    public Object handlerException(HttpServletRequest request, Exception e) {

        Map<String, Object> responseData = new HashMap<>();

        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsg());
        } else {
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }

        return CommonReturnType.create(responseData, "fail");
    }
}
