package com.yupi.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author HP
 * @create 2022/5/1 20:48
 */
@Data
public class UserRegisterRequest implements Serializable {
    /**
     * 序列化ID 防止序列化过程出现冲突
     */
    private static final long serialVersionUID = -6746623028709562990L;

    /**
     * 用户账户
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 检验密码
     */
    private String checkPassword;
}
