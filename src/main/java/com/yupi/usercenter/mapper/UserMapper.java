package com.yupi.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.vo.UserVo;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @Entity com.yupi.usercenter.model.domain.User
*/
public interface UserMapper extends BaseMapper<User> {


    @MapKey("id")
    List<Map<String, Object>> getUsers();

    UserVo obtainUser(Integer id);
}
