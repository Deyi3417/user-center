package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.dto.UpdateTicketDTO;
import com.yupi.usercenter.model.domain.vo.ExportVO;
import com.yupi.usercenter.model.domain.vo.TestVO;
import com.yupi.usercenter.model.domain.vo.UserVo;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * 服务类
 *
 * @author 刘德意
 * 2022-05-01
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      session
     * @return 返回脱敏后的用户
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 用户脱敏
     *
     * @param originUser 原始用户
     * @return 脱敏后的用户
     */
    User getSafetyUser(User originUser);

    Workbook getWorkbookByTpl(String tplName, Map<String, Object> mapList) throws FileNotFoundException;

    void renderExcel(Workbook wb, String tplName, String s);

    List<Map<String, Object>> getUsers();

    UserVo obtainUser(Integer id);

    /**
     * 获取用户列表
     *
     * @return result
     */
    List<User> getUserList();

    /**
     * 获取用户信息-导出excel
     *
     * @return result
     */
    List<ExportVO> getExportUser();

    /**
     * 获取用户信息
     *
     * @return 用户列表信息
     */
    List<TestVO> getUserVO();

    /**
     * 根据用户Id获取用户
     *
     * @param id 用户id
     * @return result
     */
    User getUserById(Integer id);
}
