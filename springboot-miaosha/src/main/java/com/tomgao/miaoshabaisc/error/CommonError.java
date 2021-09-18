package com.tomgao.miaoshabaisc.error;

/**
 * @author tomgao
 * @date 2021/8/18 6:40 下午
 */
public interface CommonError {

    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
