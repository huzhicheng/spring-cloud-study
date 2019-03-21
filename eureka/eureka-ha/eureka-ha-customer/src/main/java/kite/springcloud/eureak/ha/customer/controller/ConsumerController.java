package kite.springcloud.eureak.ha.customer.controller;

import kite.springcloud.eureak.ha.customer.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ConsumerController
 *
 * @author fengzheng
 * @date 2018/9/25
 */
@RestController
public class ConsumerController {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IHelloService helloService;

    private static final String applicationName = "ha-provider";

    @GetMapping(value = "test")
    public Object test() {
        String url = "http://" + applicationName + "/hello";
        String s = restTemplate.getForObject(url, String.class);
        return s;
    }

    @GetMapping(value = "feign")
    public Object feign() {
        String s = helloService.nice();
        return s;
    }


}
