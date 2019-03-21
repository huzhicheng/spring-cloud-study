package kite.springcloud.consul.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * MySqlConfig
 *
 * @author fengzheng
 * @date 2019/1/4
 */
@Component
@ConfigurationProperties(prefix = "mysql")
public class MySqlConfig {
    private String host;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
