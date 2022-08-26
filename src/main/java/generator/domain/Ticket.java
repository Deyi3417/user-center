package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 不合格票表
 * @TableName ticket
 */
@TableName(value ="ticket")
@Data
public class Ticket implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 票编号
     */
    private String ticket_code;

    /**
     * 票等级
     */
    private Integer ticket_level;

    /**
     * 逻辑删除标志
     */
    private Integer dlt;

    /**
     * 票处理方式
     */
    private Integer handle_way;

    /**
     * 用户id
     */
    private Long user_id;

    /**
     * 创建人id
     */
    private Long create_id;

    /**
     * 创建人姓名
     */
    private String create_name;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 修改人id
     */
    private Long modify_id;

    /**
     * 修改人姓名
     */
    private String modify_name;

    /**
     * 修改时间
     */
    private Date modify_time;

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
            && (this.getTicket_code() == null ? other.getTicket_code() == null : this.getTicket_code().equals(other.getTicket_code()))
            && (this.getTicket_level() == null ? other.getTicket_level() == null : this.getTicket_level().equals(other.getTicket_level()))
            && (this.getDlt() == null ? other.getDlt() == null : this.getDlt().equals(other.getDlt()))
            && (this.getHandle_way() == null ? other.getHandle_way() == null : this.getHandle_way().equals(other.getHandle_way()))
            && (this.getUser_id() == null ? other.getUser_id() == null : this.getUser_id().equals(other.getUser_id()))
            && (this.getCreate_id() == null ? other.getCreate_id() == null : this.getCreate_id().equals(other.getCreate_id()))
            && (this.getCreate_name() == null ? other.getCreate_name() == null : this.getCreate_name().equals(other.getCreate_name()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getModify_id() == null ? other.getModify_id() == null : this.getModify_id().equals(other.getModify_id()))
            && (this.getModify_name() == null ? other.getModify_name() == null : this.getModify_name().equals(other.getModify_name()))
            && (this.getModify_time() == null ? other.getModify_time() == null : this.getModify_time().equals(other.getModify_time()))
            && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTicket_code() == null) ? 0 : getTicket_code().hashCode());
        result = prime * result + ((getTicket_level() == null) ? 0 : getTicket_level().hashCode());
        result = prime * result + ((getDlt() == null) ? 0 : getDlt().hashCode());
        result = prime * result + ((getHandle_way() == null) ? 0 : getHandle_way().hashCode());
        result = prime * result + ((getUser_id() == null) ? 0 : getUser_id().hashCode());
        result = prime * result + ((getCreate_id() == null) ? 0 : getCreate_id().hashCode());
        result = prime * result + ((getCreate_name() == null) ? 0 : getCreate_name().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getModify_id() == null) ? 0 : getModify_id().hashCode());
        result = prime * result + ((getModify_name() == null) ? 0 : getModify_name().hashCode());
        result = prime * result + ((getModify_time() == null) ? 0 : getModify_time().hashCode());
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
        sb.append(", ticket_code=").append(ticket_code);
        sb.append(", ticket_level=").append(ticket_level);
        sb.append(", dlt=").append(dlt);
        sb.append(", handle_way=").append(handle_way);
        sb.append(", user_id=").append(user_id);
        sb.append(", create_id=").append(create_id);
        sb.append(", create_name=").append(create_name);
        sb.append(", create_time=").append(create_time);
        sb.append(", modify_id=").append(modify_id);
        sb.append(", modify_name=").append(modify_name);
        sb.append(", modify_time=").append(modify_time);
        sb.append(", source=").append(source);
        sb.append(", price=").append(price);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}