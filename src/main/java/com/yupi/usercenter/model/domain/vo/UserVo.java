package com.yupi.usercenter.model.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yupi.usercenter.model.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HP
 * @date 2022/07/01 20:56
 */
@EqualsAndHashCode(callSuper = true)
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
