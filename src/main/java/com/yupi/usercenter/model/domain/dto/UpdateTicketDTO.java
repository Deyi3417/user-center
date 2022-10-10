package com.yupi.usercenter.model.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * @author : HP
 * @date : 2022/9/2
 */
@ApiModel(value = "UpdateTicketDTO", description = "更新票传输DTO")
@Data
public class UpdateTicketDTO implements Serializable {

    private static final long serialVersionUID = 588334238484827037L;

    @ApiModelProperty(value = "主键id", name = "id")
    private Long id;

    @ApiModelProperty(value = "票等级", name = "ticketLevel")
    private Integer ticketLevel;

    @ApiModelProperty(value = "票处理方式", name = "handleWay")
    private Integer handleWay;

    @ApiModelProperty(value = "票的价格(小数点后两位)", name = "price")
    private Double price;
}
