package com.yupi.usercenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.mapper.QcResponsibleDepartmentMapper;
import com.yupi.usercenter.mapstruct.basic.DepartmentConvert2DTO;
import com.yupi.usercenter.mapstruct.basic.RandomTicketConvert2DTO;
import com.yupi.usercenter.model.domain.QcResponsibleDepartment;
import com.yupi.usercenter.model.domain.vo.DepartmentVO;
import com.yupi.usercenter.model.domain.vo.RandomTicketVO;
import com.yupi.usercenter.service.QcResponsibleDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HP
 * @description 针对表【qc_responsible_department(责任部门)】的数据库操作Service实现
 * @createDate 2022-10-09 19:23:32
 */
@Service
@Slf4j
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

    @Override
    public List<DepartmentVO> getDepartmentTree() {
        // 一次性查出来，然后进行组装
        List<QcResponsibleDepartment> departments = this.list();
        List<DepartmentVO> departmentVOS = DepartmentConvert2DTO.INSTANCE.toConvertRandomTicketVO(departments);
        log.info("{}==输出获取到的数据：{}", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"), JSON.toJSONString(departmentVOS));
        // 找到根节点
        List<DepartmentVO> parentDepartments = departmentVOS.stream().filter(item -> item.getParentId() == -1).collect(Collectors.toList());
        log.info("{}==输出根部门数据：{}", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"), JSON.toJSONString(parentDepartments));
        // 根据根部门-获取子部门
        List<DepartmentVO> collect = parentDepartments.stream().map(parent -> {
            parent.setChildDepartmentList(getChildDepartments(parent, departmentVOS));
            return parent;
        }).collect(Collectors.toList());

        log.info("{}==before :输出根部门数据：{}", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"), JSON.toJSONString(parentDepartments));
        for (DepartmentVO vo : parentDepartments) {
            vo.setChildDepartmentList(getChildDepartments(vo, departmentVOS));
        }

        log.info("{}==after :输出根部门数据：{}", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"), JSON.toJSONString(parentDepartments));

        return collect;

    }

    /**
     * 根据父部门信息获取子部门
     * @param parentDepartment 父节点/根节点
     * @return 子部门信息
     */
    private List<DepartmentVO> getChildDepartments(DepartmentVO parentDepartment, List<DepartmentVO> allDepartments) {
        List<DepartmentVO> childs = allDepartments.stream().filter(m -> m.getParentId().equals(parentDepartment.getId())).collect(Collectors.toList());
        List<DepartmentVO> collect = childs.stream().map(m -> {
            m.setChildDepartmentList(getChildDepartments(m, allDepartments));
            return m;
        }).collect(Collectors.toList());
        return collect;
//        for (DepartmentVO vo : childs) {
//            vo.setChildDepartmentList(getChildDepartments(vo, allDepartments));
//        }
//        return childs;
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




