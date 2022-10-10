package com.yupi.usercenter.model.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author : HP
 * @date : 2022/10/9
 */
@Data
@ApiModel("RandomTicketVO")
public class RandomTicketVO implements Serializable {

    private static final long serialVersionUID = -5063860813607681987L;

    @ApiModelProperty(name = "id", value = "主键ID")
    private Integer id;

    @ApiModelProperty(name = "responsibleDepartmentName", value = "部门名称")
    private String responsibleDepartmentName;

    @ApiModelProperty(name = "parentId", value = "父级")
    private Integer parentId;

    @ApiModelProperty(name = "level", value = "几级部门")
    private Integer level;

    @ApiModelProperty(name = "checkout", value = "是否使用")
    private Boolean checkout;

    @ApiModelProperty(name = "ticketList", value = "树形结构")
    private List<RandomTicketVO> ticketList;


}
