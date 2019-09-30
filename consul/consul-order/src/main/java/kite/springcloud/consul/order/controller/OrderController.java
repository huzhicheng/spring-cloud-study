package kite.springcloud.consul.order.controller;

import kite.springcloud.consul.order.entity.CustomerOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProductController
 *
 * @author fengzheng
 * @date 2019/8/29
 */
@RestController
@RequestMapping(value = "order")
public class OrderController {

    @Value("${spring.application.name}")
    private String applicationName;


    @GetMapping(value = "get")
    public CustomerOrder getOrder(){
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setOrderId("9999");
        customerOrder.setProductName("MacBook Pro");
        customerOrder.setClient(applicationName);
        return customerOrder;
    }
}
