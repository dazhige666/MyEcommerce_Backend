package com.neuedu.boot002;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//程序入口
@MapperScan("com.neuedu.boot002.dao")
//指定需要生成成实现类的接口所在的包
public class Boot002Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot002Application.class, args);
    }

}
