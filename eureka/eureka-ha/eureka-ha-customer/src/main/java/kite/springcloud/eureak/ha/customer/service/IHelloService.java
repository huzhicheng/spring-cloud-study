package kite.springcloud.eureak.ha.customer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * IHelloService
 *
 * @author fengzheng
 * @date 2018/9/26
 */
@FeignClient(value = "ha-provider")
public interface IHelloService {

    @RequestMapping(value = "/hello")
    String hello();

    @RequestMapping(value = "nice")
    String nice();
}
