package com.yupi.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.vo.ExportVO;
import com.yupi.usercenter.model.domain.vo.TestVO;
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

    /**
     * 获取用户信息-导出excel
     *
     * @return result
     */
    List<ExportVO> getExportUser();

    /**
     * 获取用户列表
     * @return 用户列表信息
     */
    List<TestVO> getUserVO();

}
