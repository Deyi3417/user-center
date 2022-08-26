package com.yupi.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 不合格票表
 * @author HP
 * @TableName ticket
 */
@TableName(value ="ticket")
@Data
public class Ticket implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 票编号
     */
    private String ticketCode;

    /**
     * 票等级
     */
    private Integer ticketLevel;

    /**
     * 逻辑删除标志
     */
    @TableLogic
    private Integer dlt;

    /**
     * 票处理方式
     */
    private Integer handleWay;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人id
     */
    private Long modifyId;

    /**
     * 修改人姓名
     */
    private String modifyName;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 票的来源
     */
    private String source;

    /**
     * 票的价格(小数点后两位)
     */
    private Double price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Ticket other = (Ticket) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTicketCode() == null ? other.getTicketCode() == null : this.getTicketCode().equals(other.getTicketCode()))
            && (this.getTicketLevel() == null ? other.getTicketLevel() == null : this.getTicketLevel().equals(other.getTicketLevel()))
            && (this.getDlt() == null ? other.getDlt() == null : this.getDlt().equals(other.getDlt()))
            && (this.getHandleWay() == null ? other.getHandleWay() == null : this.getHandleWay().equals(other.getHandleWay()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCreateId() == null ? other.getCreateId() == null : this.getCreateId().equals(other.getCreateId()))
            && (this.getCreateName() == null ? other.getCreateName() == null : this.getCreateName().equals(other.getCreateName()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getModifyId() == null ? other.getModifyId() == null : this.getModifyId().equals(other.getModifyId()))
            && (this.getModifyName() == null ? other.getModifyName() == null : this.getModifyName().equals(other.getModifyName()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTicketCode() == null) ? 0 : getTicketCode().hashCode());
        result = prime * result + ((getTicketLevel() == null) ? 0 : getTicketLevel().hashCode());
        result = prime * result + ((getDlt() == null) ? 0 : getDlt().hashCode());
        result = prime * result + ((getHandleWay() == null) ? 0 : getHandleWay().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreateId() == null) ? 0 : getCreateId().hashCode());
        result = prime * result + ((getCreateName() == null) ? 0 : getCreateName().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getModifyId() == null) ? 0 : getModifyId().hashCode());
        result = prime * result + ((getModifyName() == null) ? 0 : getModifyName().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ticketCode=").append(ticketCode);
        sb.append(", ticketLevel=").append(ticketLevel);
        sb.append(", dlt=").append(dlt);
        sb.append(", handleWay=").append(handleWay);
        sb.append(", userId=").append(userId);
        sb.append(", createId=").append(createId);
        sb.append(", createName=").append(createName);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyId=").append(modifyId);
        sb.append(", modifyName=").append(modifyName);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", source=").append(source);
        sb.append(", price=").append(price);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}