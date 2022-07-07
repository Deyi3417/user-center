package com.yupi.usercenter.model.domain.vo;

import com.yupi.usercenter.model.domain.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author HP
 * @date 2022/07/01 20:56
 */
@Data
public class UserVo extends User implements Serializable {

    private static final long serialVersionUID = -6526002981169253106L;

    /**
     * 用户账户
     */
    private String genderName;

    /**
     * 用户账户
     */
    private String statusName;

    /**
     * 用户账户
     */
    private String roleName;
}
