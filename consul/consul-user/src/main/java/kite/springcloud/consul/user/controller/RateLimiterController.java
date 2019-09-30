package kite.springcloud.consul.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * RateLimiterController
 *
 * @author fengzheng
 * @date 2019/9/27
 */
@RestController
@RequestMapping(value = "limiter")
@Slf4j
public class RateLimiterController {


    @GetMapping(value = "test")
    public String test(){
        log.info("");
        return "正常返回";
    }


}
