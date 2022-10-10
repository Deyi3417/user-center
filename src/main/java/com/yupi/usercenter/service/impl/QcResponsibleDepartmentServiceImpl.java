package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.mapper.QcResponsibleDepartmentMapper;
import com.yupi.usercenter.mapstruct.basic.RandomTicketConvert2DTO;
import com.yupi.usercenter.model.domain.QcResponsibleDepartment;
import com.yupi.usercenter.model.domain.vo.RandomTicketVO;
import com.yupi.usercenter.service.QcResponsibleDepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HP
 * @description 针对表【qc_responsible_department(责任部门)】的数据库操作Service实现
 * @createDate 2022-10-09 19:23:32
 */
@Service
public class QcResponsibleDepartmentServiceImpl extends ServiceImpl<QcResponsibleDepartmentMapper, QcResponsibleDepartment>
        implements QcResponsibleDepartmentService {

    @Override
    public List<RandomTicketVO> getDepartmentList() {
        List<QcResponsibleDepartment> qcResponsibleDepartments = this.baseMapper.selectList(new QueryWrapper<QcResponsibleDepartment>().eq("parent_id", -1).eq("level", 1));
        List<RandomTicketVO> ticketVOS1 = RandomTicketConvert2DTO.INSTANCE.toConvertRandomTicketVO(qcResponsibleDepartments);
        for (RandomTicketVO department : ticketVOS1) {
            List<RandomTicketVO> ticketVOS = findChildDepartment(department);
            department.setTicketList(ticketVOS);
        }
        return ticketVOS1;
    }

    /**
     * 2022/10/10
     *
     * @param id 业务表ID
     * @return List<RandomTicketVO>
     */
    @Override
    public List<RandomTicketVO> getChooseDepartment(Integer id) {
        List<RandomTicketVO> ticketVOS1 = this.baseMapper.getParentDepartment(id);
        for (RandomTicketVO department : ticketVOS1) {
            List<RandomTicketVO> childDepartment = findChildDepartment02(department.getId());
            department.setTicketList(childDepartment);
        }
        return ticketVOS1;
    }

    private List<RandomTicketVO> findChildDepartment02(Integer parentId) {
        List<RandomTicketVO> ticketVOS = this.baseMapper.findChildDepartment(parentId);
        if (ticketVOS.size() > 0) {
            for (RandomTicketVO childDepartment : ticketVOS) {
                List<RandomTicketVO> childDepartment1 = findChildDepartment02(childDepartment.getId());
                childDepartment.setTicketList(childDepartment1);
            }
        }
        return ticketVOS;
    }

    private List<RandomTicketVO> findChildDepartment(RandomTicketVO department) {
        Integer id = department.getId();
        List<QcResponsibleDepartment> childDepartments = this.baseMapper.selectList(new QueryWrapper<QcResponsibleDepartment>().eq("parent_id", id));
        // mapstruct 转化
        List<RandomTicketVO> ticketVOS = RandomTicketConvert2DTO.INSTANCE.toConvertRandomTicketVO(childDepartments);
        if (childDepartments.size() > 0) {
            for (RandomTicketVO childDepartment : ticketVOS) {
                List<RandomTicketVO> childDepartment1 = findChildDepartment(childDepartment);
                childDepartment.setTicketList(childDepartment1);
            }
        }
        return ticketVOS;
    }
}




