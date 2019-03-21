package kite.springcloud.eureka.single.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * SingleProviderApplication
 *
 * @author fengzheng
 * @date 2018/11/20
 */
@EnableEurekaClient
@SpringBootApplication
public class SingleProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SingleProviderApplication.class, args);
    }
}
