package kite.springcloud.sleuth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SleuthController
 *
 * @author fengzheng
 * @date 2019/3/17
 */
@RestController
public class SleuthController {

    @GetMapping(value = "sleuth")
    public String say(String value){
        return "hello sleuth  " + value;
    }
}
