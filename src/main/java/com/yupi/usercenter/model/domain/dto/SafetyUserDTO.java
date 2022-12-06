package com.yupi.usercenter.model.domain.dto;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : HP
 * @date : 2022/12/6
 */
@Data
@ApiModel("获取脱敏用户")
public class SafetyUserDTO implements Serializable {

    private static final long serialVersionUID = 5394087106791753772L;

    @ApiModelProperty("用户id(主键)")
    private Long id;

    @ApiModelProperty("昵称")
    private String username;

    @ApiModelProperty("登录账号")
    private String userAccount;

    @ApiModelProperty("用户头像")
    private String avatarUrl;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("用户状态 0-正常")
    private Integer userStatus;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty("用户角色0-普通角色，1-管理员")
    private Integer userRole;
}
