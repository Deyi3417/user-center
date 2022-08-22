package com.yupi.usercenter.service.impl;
import java.io.*;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.constant.UserConstant;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.vo.ExportVO;
import com.yupi.usercenter.model.domain.vo.TestVO;
import com.yupi.usercenter.model.domain.vo.UserVo;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.mapper.UserMapper;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值
     * 用于密码加密
     */
    private static final String SALT = "liudy23";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        String regEx = ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
        Matcher matcher = Pattern.compile(regEx).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        // 校验两次密码输入是否相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账户不能重复
        QueryWrapper<User> entity = new QueryWrapper<>();
        entity.eq("userAccount", userAccount);
        Long count = userMapper.selectCount(entity);
        if (count > 0) {
            return -1;
        }
        // 密码加密
        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        System.out.println(newPassword);
        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        boolean save = this.save(user);
        if (!save) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        // 密码加密
        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> entity = new QueryWrapper<>();
        entity.eq("userAccount", userAccount);
        entity.eq("userPassword", newPassword);
        User user = userMapper.selectOne(entity);
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
        }
        // 3. 用户脱敏
        User safetyUser = getSafetyUser(user);
        // 4. 记录用户的登录态 redis 分布式登录
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE,safetyUser);
        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param originUser 原始用户
     * @return 脱敏后的用户
     */
    @Override
    public User getSafetyUser(User originUser) {
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        safetyUser.setUserRole(originUser.getUserRole());
        return safetyUser;
    }

    @Override
    @SuppressWarnings({"rawtypes","finally"})
    public Workbook getWorkbookByTpl(String tplName, Map data) throws FileNotFoundException {
        XLSTransformer transformer = new XLSTransformer();
        String templatePath = "D:/File_liudy23/Code_File" + tplName;
        File file = new File(templatePath);
        FileInputStream in = new FileInputStream(file);

        Workbook wb = null;
        try {
            wb = transformer.transformXLS(in, data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return wb;
        }
    }
    @Override
    public void renderExcel(Workbook wb, String tplName, String fileName) {
        if (fileName == null || "".equals(fileName)) {
            fileName = tplName.substring(tplName.lastIndexOf("/") + 1);
        }
        try {
            fileName = new String(fileName.getBytes("gbk"),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition","attachment;filename=\""+ fileName + "\"");
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> getUsers() {
        return this.baseMapper.getUsers();
    }

    @Override
    public UserVo obtainUser(Integer id) {
        return this.baseMapper.obtainUser(id);
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = this.baseMapper.selectList(new QueryWrapper<User>().eq("isDelete", 0));
        return userList;
    }

    @Override
    public List<ExportVO> getExportUser() {
        return this.baseMapper.getExportUser();
    }

    @Override
    public List<TestVO> getUserVO() {
        return this.baseMapper.getUserVO();
    }

    @Override
    public User getUserById(Integer id) {
        return this.baseMapper.selectById(id);
    }

}
