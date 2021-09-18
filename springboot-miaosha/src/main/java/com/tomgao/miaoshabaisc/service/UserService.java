package com.tomgao.miaoshabaisc.service;

import com.tomgao.miaoshabaisc.error.BusinessException;
import com.tomgao.miaoshabaisc.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author tomgao
 * @date 2021/8/18 5:01 下午
 */

@Service
public interface UserService {


    UserModel getUserById(Integer id);

    void register(UserModel userModel) throws BusinessException;

    UserModel validateLogin(String telphone, String encrptPassword) throws BusinessException;
}
