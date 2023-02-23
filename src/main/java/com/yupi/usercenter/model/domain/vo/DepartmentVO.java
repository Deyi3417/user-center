package com.yupi.usercenter.model.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author : HP
 * @date : 2023/2/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("部门vo")
public class DepartmentVO implements Serializable {

    private static final long serialVersionUID = 1072932998222312181L;

    @ApiModelProperty(name = "id", value = "主键id")
    private Integer id;

    @ApiModelProperty(name = "responsibleDepartmentName", value = "部门名称")
    private String responsibleDepartmentName;

    @ApiModelProperty(name = "parentId", value = "父id")
    private Integer parentId;

    @ApiModelProperty(name = "departmentList", value = "子部门")
    private List<DepartmentVO> childDepartmentList;
}