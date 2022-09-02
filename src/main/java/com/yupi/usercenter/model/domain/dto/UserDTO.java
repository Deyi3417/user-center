package com.yupi.usercenter.model.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author : HP
 * @date : 2022/9/2
 */
@Data
@ApiModel(value = "UserDTO",description = "UserDTO传输类")
public class UserDTO {

    /**
     * 用户id(主键)
     */
    @ApiModelProperty(value = "主键id", name = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", name = "username")
    private String username;

    /**
     * 登录账号
     */
    @ApiModelProperty(value = "登录账号", name = "userAccount")
    private String userAccount;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别", name = "gender")
    private Integer gender;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", name = "phone")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;

}
