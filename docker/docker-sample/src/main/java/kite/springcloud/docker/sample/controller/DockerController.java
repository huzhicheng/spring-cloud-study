package kite.springcloud.docker.sample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DockerController
 *
 * @author fengzheng
 * @date 2019/1/18
 */
@RestController
public class DockerController {

    @GetMapping(value = "hello")
    public Object sayHello(){
        return "hello! spring boot with docker.";
    }
}
