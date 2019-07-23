package kite.springcloud.only.security.config.two;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * KiteUserDetail
 *
 * @author fengzheng 古时的风筝
 * @date 2019/3/30
 */
@Data
public class KiteUserDetails implements UserDetails {

    public KiteUserDetails(String username,String password, List<? extends GrantedAuthority> authorities,
                           boolean accountNonExpired, boolean accountNonLocked,
                           boolean credentialsNonExpired, boolean enabled,String extra) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.extra = extra;
    }

    public KiteUserDetails(String username, String password, List<? extends GrantedAuthority> authorities, String extra) {
        this(username, password, authorities,
                true,true,
                true,true,extra);
    }

    private String password;

    private final String username;

    private final List<? extends GrantedAuthority> authorities;

    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    private String extra;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
