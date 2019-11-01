package kite.springcloud.oauth2.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * HelloController
 *
 * @author fengzheng
 * @date 2019/10/18
 */
@RestController
@RequestMapping(value = "hello")
public class HelloController {


    public static void main(String[] args){
        System.out.println(new BCryptPasswordEncoder().encode("user-secret-8888"));
        System.out.println(new BCryptPasswordEncoder().encode("client-secret-8888"));
        System.out.println(new BCryptPasswordEncoder().encode("code-secret-8888"));
    }

}
