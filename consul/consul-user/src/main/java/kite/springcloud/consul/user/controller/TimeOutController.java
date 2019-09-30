package kite.springcloud.consul.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TimeOutController
 *
 * @author fengzheng
 * @date 2019/9/26
 */
@RestController
@RequestMapping(value = "timeout")
public class TimeOutController {

    @GetMapping(value = "ok")
    public String ok(){
        return "我没有超时";
    }

    @GetMapping(value = "fail")
    public String fail() throws Exception{
        Thread.sleep(3000);
        return "我可能超时了，因为我睡了 3 秒";
    }
}
