package kite.springcloud.consul.customer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * IHelloService
 *
 * @author fengzheng
 * @date 2018/9/26
 */
@FeignClient(value = "consul-provider")
public interface IHelloService {

    @RequestMapping(value = "/test")
    String test();

    @RequestMapping(value = "nice")
    String nice();
}
