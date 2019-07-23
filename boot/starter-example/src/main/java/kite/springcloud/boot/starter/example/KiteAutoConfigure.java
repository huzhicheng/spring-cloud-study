package kite.springcloud.boot.starter.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * KiteAutoConfigure
 *
 * @author fengzheng
 * @date 2019/5/20
 */
@Configuration
@ConditionalOnClass(KiteService.class)
@EnableConfigurationProperties(KiteProperties.class)
@Slf4j
public class KiteAutoConfigure {

    @Autowired
    private KiteProperties kiteProperties;

    @Bean
    @ConditionalOnMissingBean(KiteService.class)
    @ConditionalOnProperty(prefix = "kite.example",value = "enabled", havingValue = "true")
    KiteService kiteService(){
        return new KiteService(kiteProperties);
    }
}
