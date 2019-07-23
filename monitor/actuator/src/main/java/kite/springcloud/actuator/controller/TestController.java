package kite.springcloud.actuator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author fengzheng
 * @date 2019/7/15
 */
@RestController
@RequestMapping(value = "rest")
public class TestController {

    @GetMapping(value = "getString")
    public String getString(){
        return "hello spring boot actutor";
    }
}
