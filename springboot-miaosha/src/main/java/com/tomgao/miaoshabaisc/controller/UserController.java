package com.tomgao.miaoshabaisc.controller;

import com.tomgao.miaoshabaisc.controller.vo.UserVo;
import com.tomgao.miaoshabaisc.error.BusinessException;
import com.tomgao.miaoshabaisc.error.EmBusinessError;
import com.tomgao.miaoshabaisc.response.CommonReturnType;
import com.tomgao.miaoshabaisc.service.UserService;
import com.tomgao.miaoshabaisc.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author tomgao
 * @date 2021/8/18 5:00 下午
 */

@RestController("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    // 采用 ThreadLocal 保证每个request 的HttpServletRequest 都是私有 并且是非单例
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping("/get")
    public CommonReturnType getUser(@RequestParam(name="id") Integer id) throws BusinessException {

        UserModel userModel = userService.getUserById(id);

        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVo userVo = convertFromModel(userModel);

        return CommonReturnType.create(userVo);
    }

    // 用户注册接口
    @PostMapping("/register")
    @ResponseBody
    public CommonReturnType register(String telphone,
                                     String otpCode,
                                     String name,
                                     String password,
                                     Integer gender,
                                     Integer age) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //验证手机号和验证码
        String sessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if (!StringUtils.equals(otpCode,sessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "验证码错误");
        }
        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setTelphone(telphone);
        userModel.setAge(age);
        userModel.setGender(gender);
        userModel.setName(name);
        userModel.setRegisterMode("byPhone");

        // 密码加密
        userModel.setEncrptPassword(this.EncodeByMd5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    // 用户登录接口
    @PostMapping("/login")
    @ResponseBody
    public CommonReturnType login(@RequestParam("telephone") String telphone, String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //入参校验
        if (StringUtils.isEmpty(telphone) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telphone, this.EncodeByMd5(password));

        // 将登录凭证加入到用户登录成功的 session 内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);

        return CommonReturnType.create(null);
    }

    public String EncodeByMd5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();

        //加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }

    //用户获取 otp 短信接口
    @PostMapping("/getotp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam("telphone") String telphone) {

        //按照一定规则生成 OTP 验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String optCode = String.valueOf(randomInt);

        // 将 OTP 验证码同对应用户的手机关联,可以用 Redis, 这里用 httpSession 的方式绑定手机号和 OptCode
        httpServletRequest.getSession().setAttribute(telphone, optCode);

        // 将 OTP 验证码通过短信通道发送给用户, 省略
        System.out.println("telphone = " + telphone + "& optCode = " + optCode);

        return CommonReturnType.create(null);
    }

    private UserVo convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userModel, userVo);
        return userVo;
    }
}
