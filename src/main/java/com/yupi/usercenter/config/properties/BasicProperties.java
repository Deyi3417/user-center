package com.yupi.usercenter.config.properties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * 配置文件中基本的参数信息
 *
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

    @ApiModelProperty(name = "fileUploadPath", value = "文件上传路径")
    private String fileUploadPath;

    @ApiModelProperty(name = "fileSavePath", value = "文件保存路径")
    private String fileSavePath;

    @ApiModelProperty(name = "address", value = "配置文件中的 address")
    private String address;
}
