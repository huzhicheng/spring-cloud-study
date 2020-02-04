package kite.springcloud.consul.user.controller;

import kite.springcloud.consul.user.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author fengzheng
 * @date 2019/8/29
 */
@RestController
@RequestMapping(value = "user")
public class UserController {

    @GetMapping(value = "get")
    public User getUserInfo(){
        User user = new User();
        user.setName("古时的风筝");
        user.setAge(8);
        user.setLocation("北京");
        return user;
    }


}
