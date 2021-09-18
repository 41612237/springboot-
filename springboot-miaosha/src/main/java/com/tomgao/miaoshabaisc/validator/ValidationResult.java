package com.tomgao.miaoshabaisc.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tomgao
 * @date 2021/9/3 4:39 下午
 */
@Data
public class ValidationResult {

    private boolean hasErrors = false;

    //存放错误信息的 map
    private Map<String, String> errorMsgMap = new HashMap<>();

    // 实现通用的通过格式化字符串信息获取错误结果的 msg 方法
    public String getErrMsg() {
        return StringUtils.join(errorMsgMap.values().toArray(), ",");
    }
}
