package com.tomgao.miaoshabaisc.response;

import lombok.Data;

/**
 * @author tomgao
 * @date 2021/8/18 6:29 下午
 */
@Data
public class CommonReturnType {

//    对应请求的返回处理结果 "success" "fail"
    private String status;

//    若 status=success,则 data 内返回前端需要的json 数据
//    status= fail, 则 data 内使用通用的错误码格式
    private Object data;

    public static CommonReturnType create(Object result) {
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }
}
