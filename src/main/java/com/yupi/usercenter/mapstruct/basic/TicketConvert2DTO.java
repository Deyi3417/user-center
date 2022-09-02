package com.yupi.usercenter.mapstruct.basic;

import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.model.domain.dto.TicketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author : HP
 * @date : 2022/9/2
 */
@Mapper(componentModel = "spring")
public interface TicketConvert2DTO {

    TicketConvert2DTO INSTANCE = Mappers.getMapper(TicketConvert2DTO.class);

    /**
     * 将 Ticket 实体类 转换为 TicketDTO
     * @param ticket 票实体类对象
     * @return TicketDTO
     */
    TicketDTO toConvertTicketDTO(Ticket ticket);
}
