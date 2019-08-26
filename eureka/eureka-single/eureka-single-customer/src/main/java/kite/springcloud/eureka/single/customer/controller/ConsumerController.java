package kite.springcloud.eureka.single.customer.controller;

import kite.springcloud.eureka.single.customer.service.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private static final String applicationName = "single-provider";

    @RequestMapping(value = "feignRequest")
    public Object feignRequest(){

        String s = helloService.nice();
        return s;
    }

    @RequestMapping(value = "commonRequest")
    public Object commonRequest(){
        String url = "http://"+ applicationName +"/hello";
        String s = restTemplate.getForObject(url,String.class);
        return s;
    }


    @GetMapping(value = "donothing")
    public String donothing(){
        return "hello eureka!";
    }


    @GetMapping(value = "timeout")
    public String timeout(){
        return helloService.timeOut();
    }
}
