package kite.springcloud.only.security.config.two;

import kite.springcloud.only.security.config.one.KiteUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * OneSecurityConfig
 *
 * @author fengzheng 古时的风筝
 * @date 2019/3/30
 */
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TwoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
       // auth.authenticationProvider().passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args){

        // $2a$10$cMFMu9A3R69USaoM0NFVuOA8seaZix3M4Im/gh2llbVZceafrKBkq
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
