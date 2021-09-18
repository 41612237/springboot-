package com.tomgao.miaoshabaisc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tomgao.miaoshabaisc"})
@MapperScan("com.tomgao.miaoshabaisc.dao") //这个版本必须要加这个
public class MiaoshabaiscApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaoshabaiscApplication.class, args);
    }

}
