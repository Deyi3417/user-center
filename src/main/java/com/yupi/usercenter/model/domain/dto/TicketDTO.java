package com.yupi.usercenter.model.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.yupi.usercenter.model.domain.Ticket;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author : HP
 * @date : 2022/8/26
 */
@Data
@ApiModel(value = "TicketDTO", description = "Ticket 脱敏")
public class TicketDTO implements Serializable {

    private static final long serialVersionUID = 2738439400515542027L;

    @ApiModelProperty(value = "主键id", name = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "票编号", name = "ticketCode")
    private String ticketCode;

    @ApiModelProperty(value = "票等级", name = "ticketLevel")
    private Integer ticketLevel;

    @ApiModelProperty(value = "票处理方式", name = "handleWay")
    private Integer handleWay;

    @ApiModelProperty(value = "票的来源", name = "source")
    private String source;

    @ApiModelProperty(value = "票的价格(小数点后两位)", name = "price")
    private Double price;
}
