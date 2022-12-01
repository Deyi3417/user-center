package com.yupi.usercenter.model.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : HP
 * @date : 2022/11/30
 */
@Data
@ApiModel("下拉选择用户")
public class UserPullVO implements Serializable {

    private static final long serialVersionUID = 6403298879952898942L;

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("昵称")
    private String username;
}
