package com.yupi.usercenter.config.properties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author : HP
 * @date : 2023/3/16
 */
@Data
@Configuration
@ConfigurationProperties(prefix = BasicProperties.PREFIX)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("配置文件中基本的参数信息")
public class BasicProperties implements Serializable {

    private static final long serialVersionUID = -8530307978299621486L;

    public static final String PREFIX = "basic";

    @ApiModelProperty(name = "userName", value = "配置文件中的 user_name")
    private String userName;

    @ApiModelProperty(name = "userGender", value = "配置文件中的 user-gender")
    private String userGender;

    @ApiModelProperty(name = "address", value = "配置文件中的 address")
    private String address;
}
