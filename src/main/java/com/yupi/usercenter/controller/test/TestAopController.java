package com.yupi.usercenter.controller.test;

import com.yupi.usercenter.aop.test.OrderService;
import com.yupi.usercenter.aop.test.SaveOrder;
import com.yupi.usercenter.aop.test.UpdateOrder;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : HP
 * @date : 2023/2/15
 */
@RestController
@RequestMapping("/aop")
@Api(tags = "测试面向切面编程")
public class TestAopController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    @ApiOperation("测试aop")
    public BaseResponse<?> testAop() {
        SaveOrder saveOrder = new SaveOrder();
        saveOrder.setId(1L);
        orderService.saveOrder(saveOrder);

        UpdateOrder updateOrder = new UpdateOrder();
        updateOrder.setOrderId(2L);
        orderService.updateOrder(updateOrder);
        return ResultUtils.success();
    }
}
