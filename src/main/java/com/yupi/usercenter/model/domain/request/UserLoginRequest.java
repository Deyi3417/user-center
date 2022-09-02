package com.yupi.usercenter.model.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HP
 * @create 2022/5/1 20:56
 */
@Data
@ApiModel(value = "用户登录请求类",description = "UserLoginRequest")
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 7851750037521868036L;
    /**
     * 用户账户
     */
    @ApiModelProperty(value = "用户账号", name = "userAccount")
    private String userAccount;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码", name = "userPassword")
    private String userPassword;
}
