package com.yupi.usercenter.model.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : HP
 * @date : 2022/11/17
 */
@Data
@ApiModel("分页查询DTO-包括模糊搜索/精确搜索")
public class PageSearchDTO implements Serializable {

    private static final long serialVersionUID = -7266519379801876659L;

    @ApiModelProperty(name = "current", value = "当前页")
    private Long current;

    @ApiModelProperty(name = "size", value = "每页展示几条数据")
    private Long size;

    @ApiModelProperty(name = "condition", value = "模糊查询条件")
    private String condition;

    @ApiModelProperty(name = "userId", value = "用户id")
    private Integer userId;
}
