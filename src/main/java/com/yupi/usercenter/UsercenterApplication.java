package com.yupi.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.yupi.usercenter.mapper")
public class UsercenterApplication {

    public static void main(String[] args) {
        System.out.println("========start running========");
        SpringApplication.run(UsercenterApplication.class, args);
        System.out.println("========Start successfully========");
    }

}
