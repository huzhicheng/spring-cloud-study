package kite.springcloud.eureka.ha.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * HelloController
 *
 * @author fengzheng
 * @date 2018/9/21
 */
@Slf4j
@RestController
public class HelloController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "hello")
    public String hello(){
        List<String> services = discoveryClient.getServices();
        for(String s : services){
            log.info(s);
        }
        return "hello spring cloud!";
    }

    @GetMapping(value = "nice")
    public String nice(){
        List<String> services = discoveryClient.getServices();
        for(String s : services){
            log.info("gogogo" + s);
        }
        return "nice to meet you!";
    }
}
