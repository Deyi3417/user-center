package com.yupi.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.usercenter.model.domain.QcResponsibleDepartment;
import com.yupi.usercenter.model.domain.vo.RandomTicketVO;

import java.util.List;

/**
 * @author HP
 * @description 针对表【qc_responsible_department(责任部门)】的数据库操作Mapper
 * @createDate 2022-10-09 19:23:32
 * @Entity com.yupi.usercenter.model.domain.QcResponsibleDepartment
 */
public interface QcResponsibleDepartmentMapper extends BaseMapper<QcResponsibleDepartment> {

    /**
     * 得到父部门信息
     *
     * @param id 业务表主键ID
     * @return List<RandomTicketVO>
     */
    List<RandomTicketVO> getParentDepartment(Integer id);

    /**
     * 搜索子部门
     *
     * @param parentId 父部门ID
     * @return
     */
    List<RandomTicketVO> findChildDepartment(Integer parentId);
}




