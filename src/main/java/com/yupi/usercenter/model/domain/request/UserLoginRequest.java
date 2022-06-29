package com.yupi.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HP
 * @create 2022/5/1 20:56
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 7851750037521868036L;
    /**
     * 用户账户
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;
}
