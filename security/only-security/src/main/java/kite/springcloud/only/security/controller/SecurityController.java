package kite.springcloud.only.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SecurityController
 *
 * @author fengzheng 古时的风筝
 * @date 2019/3/29
 */
@Slf4j
@RestController
public class SecurityController {

    @GetMapping(value = "test")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String runSecurity(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName());
        return "hello security";
    }

    @GetMapping(value = "test2")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String runSecurity2(){
        return "hello security2";
    }
}
