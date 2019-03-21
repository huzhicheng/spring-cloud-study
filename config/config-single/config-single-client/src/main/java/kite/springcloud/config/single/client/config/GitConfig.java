package kite.springcloud.config.single.client.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * GitConfig
 * 配合调用 actuator/refresh 方法 刷新页面依然是旧值
 * @author fengzheng
 * @date 2019/3/21
 */
@Data
@Component
public class GitConfig {

    @Value("${data.env}")
    private String env;

    @Value("${data.user.username}")
    private String username;

    @Value("${data.user.password}")
    private String password;

}
