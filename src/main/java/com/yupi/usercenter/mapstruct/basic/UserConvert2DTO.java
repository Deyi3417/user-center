package com.yupi.usercenter.mapstruct.basic;

import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.SafetyUserDTO;
import com.yupi.usercenter.model.domain.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * User实体类映射为 UserDTO
 *
 * @author : HP
 * @date : 2022/9/2
 */
@Mapper(componentModel = "spring")
public interface UserConvert2DTO {
    UserConvert2DTO INSTANCE = Mappers.getMapper(UserConvert2DTO.class);

    /**
     * 将 User 实体类 转换为 UserDTO
     *
     * @param user 用户实体类对象
     * @return UserDTO
     */
    UserDTO toCovertUserDTO(User user);

    /**
     * List<User> to List<UserDTO>
     *
     * @param userList 用户列表
     * @return List<UserDTO>
     */
    List<UserDTO> toCovetUserDTOList(List<User> userList);

    /**
     * userDTO 转化为 user
     *
     * @param userDTO userDTO
     * @return User
     */
    User toConvertUser(UserDTO userDTO);

    /**
     * User脱敏
     *
     * @param user 用户
     * @return SafetyUserDTO
     */
    SafetyUserDTO toCovetSafetyUserDTO(User user);

    /**
     * 用户信息脱敏
     *
     * @param users 用户list
     * @return List<SafetyUserDTO>
     */
    List<SafetyUserDTO> toCovetSafetyUserDTOList(List<User> users);
}
