package com.tomgao.miaoshabaisc.error;

/**
 * @author tomgao
 * @date 2021/8/18 6:42 下午
 */
public enum EmBusinessError implements CommonError{

//    通用错误乐熊
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
//    未知错误
    UNKNOWN_ERROR(10002, "参数不合法"),

//    20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001, "用户不存在"),

    USER_LOGIN_FAILED(20002, "手机号或密码不正确"),

    //30000开头为 交易信息错误定义
    STOCK_NOT_ENOUGH(30001, "库存不足"),
    ;

    private int errCode;
    private String errMsg;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
