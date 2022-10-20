package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 随机不合格票责任部门关联表，两者是多对多的关系，一个 随机不合格票 可以对应 多个 责任部门；任意一个 责任部门 可以对
 * @TableName qc_random_unqualifiedticket_department
 */
@TableName(value ="qc_random_unqualifiedticket_department")
@Data
public class QcRandomUnqualifiedticketDepartment implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer randomUnqualifiedTicketId;

    /**
     * 
     */
    private Integer level;

    /**
     * 
     */
    private Integer responsibleDepartmentId;

    /**
     * 用来保存 责任部门的扩展信息，比如 商务责任时，保存 供应商信息，制造责任，选择“其他”时，保存页面输入值
     */
    private String attr;

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
        QcRandomUnqualifiedticketDepartment other = (QcRandomUnqualifiedticketDepartment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRandomUnqualifiedTicketId() == null ? other.getRandomUnqualifiedTicketId() == null : this.getRandomUnqualifiedTicketId().equals(other.getRandomUnqualifiedTicketId()))
            && (this.getLevel() == null ? other.getLevel() == null : this.getLevel().equals(other.getLevel()))
            && (this.getResponsibleDepartmentId() == null ? other.getResponsibleDepartmentId() == null : this.getResponsibleDepartmentId().equals(other.getResponsibleDepartmentId()))
            && (this.getAttr() == null ? other.getAttr() == null : this.getAttr().equals(other.getAttr()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRandomUnqualifiedTicketId() == null) ? 0 : getRandomUnqualifiedTicketId().hashCode());
        result = prime * result + ((getLevel() == null) ? 0 : getLevel().hashCode());
        result = prime * result + ((getResponsibleDepartmentId() == null) ? 0 : getResponsibleDepartmentId().hashCode());
        result = prime * result + ((getAttr() == null) ? 0 : getAttr().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", randomUnqualifiedTicketId=").append(randomUnqualifiedTicketId);
        sb.append(", level=").append(level);
        sb.append(", responsibleDepartmentId=").append(responsibleDepartmentId);
        sb.append(", attr=").append(attr);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}