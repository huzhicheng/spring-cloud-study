package kite.springcloud.consul.oauth.server.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author fengzheng
 * @date 2019/10/18
 */
@RestController
@RequestMapping(value = "hello")
public class HelloController {

    @GetMapping(value = "get")
    public String getUserInfo(){

        return "hello";
    }
    public static void main(String[] args){
        System.out.println(new BCryptPasswordEncoder().encode("user-secret-8888"));
        System.out.println(new BCryptPasswordEncoder().encode("client-secret-8888"));
        System.out.println(new BCryptPasswordEncoder().encode("code-secret-8888"));
    }

}
