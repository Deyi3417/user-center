package com.yupi.usercenter.aop.test;

import org.springframework.stereotype.Service;

/**
 * @author : HP
 * @date : 2023/2/15
 */
@Service
public class OrderService {

    public Boolean saveOrder(SaveOrder saveOrder) {
        System.out.println("save order, orderId : " + saveOrder.getId());
        return true;
    }

    public Boolean updateOrder(UpdateOrder updateOrder) {
        System.out.println("update order, orderId : " + updateOrder.getOrderId());
        return true;
    }

}
