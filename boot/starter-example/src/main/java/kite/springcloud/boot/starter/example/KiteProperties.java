package kite.springcloud.boot.starter.example;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * KiteProperties
 *
 * @author fengzheng
 * @date 2019/5/20
 */
@Data
@ConfigurationProperties("kite.example")
public class KiteProperties {

    private String host;

    private int port;
}
