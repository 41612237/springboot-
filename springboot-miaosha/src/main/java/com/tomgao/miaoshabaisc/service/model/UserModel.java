package com.tomgao.miaoshabaisc.service.model;

import lombok.Data;

/**
 * @author tomgao
 * @date 2021/8/18 5:08 下午
 */
@Data
public class UserModel {

    private Integer id;
    private String name;
    private Integer gender;
    private Integer age;

    private String telphone;
    private String registerMode;
    private String thirdPartyId;

    private String encrptPassword;
}
