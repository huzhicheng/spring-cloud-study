package kite.springcloud.consul.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MySqlComplexConfig
 *
 * @author fengzheng
 * @date 2019/1/4
 */
@Component
@ConfigurationProperties(prefix = "mysql")
public class MySqlComplexConfig {

    public static class UserInfo{

        private String username;

        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    private String host;

    private UserInfo user;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

}
