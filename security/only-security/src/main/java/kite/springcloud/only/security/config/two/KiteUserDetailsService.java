package kite.springcloud.only.security.config.one;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * KiteUserDetailsService
 *
 * @author fengzheng 古时的风筝
 * @date 2019/3/30
 */
@Slf4j
@Component(value = "kiteUserDetailsService")
public class KiteUserDetailsService implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("usernameis:" + username);
        // 查询数据库操作
        if(!username.equals("admin")){
            throw new UsernameNotFoundException("the user is not found");
        }else{
            // 用户角色也应在数据库中获取
            String role = "ROLE_ADMIN";
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(role));
            String password = "$2a$10$cMFMu9A3R69USaoM0NFVuOA8seaZix3M4Im/gh2llbVZceafrKBkq";
            // 返回默认的 User
            // return new org.springframework.security.core.userdetails.User(username,password, authorities);

            // 返回自定义的 KiteUserDetails
            KiteUserDetails kiteUserDetails = new KiteUserDetails(username,password,authorities,"额外信息");
            return kiteUserDetails;
        }
    }
}
