package com.yupi.usercenter.mapstruct.basic;

import com.yupi.usercenter.model.domain.QcResponsibleDepartment;
import com.yupi.usercenter.model.domain.vo.DepartmentVO;
import com.yupi.usercenter.model.domain.vo.RandomTicketVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : HP
 * @date : 2022/9/2
 */
@Mapper(componentModel = "spring")
public interface DepartmentConvert2DTO {

    DepartmentConvert2DTO INSTANCE = Mappers.getMapper(DepartmentConvert2DTO.class);

    /**
     * 将 QcResponsibleDepartment 实体类 转换为 RandomTicketVO
     * @param departments 部门类信息
     * @return List<RandomTicketVO>
     */
    List<DepartmentVO> toConvertRandomTicketVO(List<QcResponsibleDepartment> departments);

}
