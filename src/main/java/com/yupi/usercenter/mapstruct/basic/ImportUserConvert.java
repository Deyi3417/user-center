package com.yupi.usercenter.mapstruct.basic;

import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.ImportUserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author : HP
 * @date : 2023/2/13
 */
@Mapper(componentModel = "spring")
public interface ImportUserConvert {

    ImportUserConvert INSTANCE = Mappers.getMapper(ImportUserConvert.class);
    /**
     * 将ImportUserData 转为 User实体
     *
     * @param data 元数据
     * @return User实体
     */
    @Mapping(source = "name", target = "username")
    @Mapping(source = "account", target = "userAccount")
    @Mapping(target = "gender", expression = "java(com.yupi.usercenter.enums.GenderEnum.getCodeByName(data.getGender()))")
    @Mapping(source = "status", target = "userStatus")
    User toUser(ImportUserData data);

    /**
     * 将List<ImportUserData> 转为 List<User>
     *
     * @param dataList 元数据
     * @return User实体
     */
    List<User> toUserList(List<ImportUserData> dataList);
}
