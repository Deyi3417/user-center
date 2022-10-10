package com.yupi.usercenter.mapstruct.basic;

import com.yupi.usercenter.model.domain.QcResponsibleDepartment;
import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.model.domain.dto.TicketDTO;
import com.yupi.usercenter.model.domain.vo.RandomTicketVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : HP
 * @date : 2022/9/2
 */
@Mapper(componentModel = "spring")
public interface RandomTicketConvert2DTO {

    RandomTicketConvert2DTO INSTANCE = Mappers.getMapper(RandomTicketConvert2DTO.class);

    /**
     * 将 QcResponsibleDepartment 实体类 转换为 RandomTicketVO
     * @param departments 部门类信息
     * @return List<RandomTicketVO>
     */
    List<RandomTicketVO> toConvertRandomTicketVO(List<QcResponsibleDepartment> departments);

}
