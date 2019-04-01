package kite.springcloud.only.security.config.two;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * KiteAuthenticationProvider
 *
 * @author fengzheng 古时的风筝
 * @date 2019/3/30
 */
@Component(value = "kiteAuthenticationProvider")
public class KiteAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Object object = authentication.getPrincipal();
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
