package com.yupi.usercenter.controller.test;

import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.config.properties.BasicProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : HP
 * @ConfigurationProperties 注解是一个Spring框架中的注解，用于将配置文件中的属性值绑定到Java对象上。
 * 通过这个注解，我们可以方便地将一个配置文件的属性值注入到一个Java对象中，从而实现对应用程序的配置和管理。
 * @date : 2023/3/16
 */
@RestController
@Api(tags = "参数自动加载控制器")
@Slf4j
@AllArgsConstructor
public class PropertiesAutoLoadingController {

    private BasicProperties basicProperties;

    @GetMapping("getProperties")
    @ApiOperation("获取配置文件参数")
    public BaseResponse<?> getProperties() {
        log.info("LIUDY23: " +basicProperties);
//        return ResultUtils.success(basicProperties.toString());
        return ResultUtils.success(basicProperties);// 此种输出值会出错
    }
}
