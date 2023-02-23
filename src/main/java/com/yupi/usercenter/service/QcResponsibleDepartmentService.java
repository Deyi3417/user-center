package com.yupi.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.model.domain.QcResponsibleDepartment;
import com.yupi.usercenter.model.domain.vo.DepartmentVO;
import com.yupi.usercenter.model.domain.vo.RandomTicketVO;

import java.util.List;

/**
 * @author HP
 * @description 针对表【qc_responsible_department(责任部门)】的数据库操作Service
 * @createDate 2022-10-09 19:23:32
 */
public interface QcResponsibleDepartmentService extends IService<QcResponsibleDepartment> {

    /**
     * 获取部门信息
     *
     * @return List<RandomTicketVO>
     */
    List<RandomTicketVO> getDepartmentList();

    /**
     * 根据业务ID获取选择的部门信息
     *
     * @param id 业务表ID
     * @return List<RandomTicketVO>
     */
    List<RandomTicketVO> getChooseDepartment(Integer id);

    /**
     * 获取部门结构树
     *
     * @return List<DepartmentVO>
     */
    List<DepartmentVO> getDepartmentTree();
}
