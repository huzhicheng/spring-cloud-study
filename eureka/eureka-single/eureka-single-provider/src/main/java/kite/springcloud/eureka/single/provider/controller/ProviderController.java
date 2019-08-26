package kite.springcloud.eureka.single.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ProviderController
 *
 * @author fengzheng
 * @date 2018/11/20
 */
@Slf4j
@RestController
public class ProviderController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/timeout")
    public String timeOut() throws Exception{
        Thread.sleep(7000);
        return "wait 7 seconds timeout";
    }

    @RequestMapping(value = "/hello")
    public String hello(){
        List<String> services = discoveryClient.getServices();
        for(String s : services){
            log.info(s);
        }
        return "hello spring cloud!";
    }

    @RequestMapping(value = "/nice")
    public String nice(){
        List<String> services = discoveryClient.getServices();
        for(String s : services){
            log.info("gogogo" + s);
        }
        return "nice to meet you!";
    }

}
