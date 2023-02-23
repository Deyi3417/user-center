package com.yupi.usercenter.controller.ticket;

import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.model.domain.vo.DepartmentVO;
import com.yupi.usercenter.model.domain.vo.RandomTicketVO;
import com.yupi.usercenter.service.QcResponsibleDepartmentService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : HP
 * @date : 2022/10/9
 */
@RestController
@RequestMapping("/randomTicket")
@Slf4j
public class RandomTicketController {

    @Autowired
    private QcResponsibleDepartmentService departmentService;

    @GetMapping("/getList")
    @ApiOperation("获取部门信息")
    public BaseResponse<List<RandomTicketVO>> getList() {
        List<RandomTicketVO> randomTicketVOList = departmentService.getDepartmentList();
        return ResultUtils.success(randomTicketVOList);
    }

    @GetMapping("/departmentTree")
    @ApiOperation("获取责任部门树形结构")
    public BaseResponse<List<DepartmentVO>> getDepartmentTree() {
        List<DepartmentVO> tree = departmentService.getDepartmentTree();
        return ResultUtils.success(tree);
    }


    @GetMapping("/chooseDepartment")
    @ApiOperation("获取不合格处置已选择的部门信息")
    public BaseResponse<List<RandomTicketVO>> chooseDepartment(@RequestParam Integer id) {
        List<RandomTicketVO> randomTicketVOList = departmentService.getChooseDepartment(id);
        return ResultUtils.success(randomTicketVOList);
    }
}
