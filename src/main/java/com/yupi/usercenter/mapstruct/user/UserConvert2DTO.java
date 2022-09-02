package com.yupi.usercenter.mapstruct.user;

import com.yupi.usercenter.model.domain.Ticket;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.TicketDTO;
import com.yupi.usercenter.model.domain.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * User实体类映射为 UserDTO
 * @author : HP
 * @date : 2022/9/2
 */
@Mapper(componentModel = "spring")
public interface UserConvert2DTO {
    UserConvert2DTO INSTANCE = Mappers.getMapper(UserConvert2DTO.class);

    /**
     * 将 User 实体类 转换为 UserDTO
     * @param user 用户实体类对象
     * @return UserDTO
     */
    UserDTO toCovertUserDTO(User user);

    /**
     * 将 Ticket 实体类 转换为 TicketDTO
     * @param ticket 票实体类对象
     * @return TicketDTO
     */
    TicketDTO toConvertTicketDTO(Ticket ticket);
}
