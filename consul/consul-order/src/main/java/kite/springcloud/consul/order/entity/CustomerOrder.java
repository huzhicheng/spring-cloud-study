package kite.springcloud.consul.order.entity;

import lombok.Data;

/**
 * CustomerOrder
 *
 * @author fengzheng
 * @date 2019/8/29
 */
@Data
public class CustomerOrder {

    private String orderId;

    private String productName;

    private String client;
}
