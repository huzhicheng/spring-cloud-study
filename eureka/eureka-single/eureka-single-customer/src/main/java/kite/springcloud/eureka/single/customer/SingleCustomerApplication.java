package kite.springcloud.eureka.single.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Author  fengzheng
 * SingleCustomerApplication
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SingleCustomerApplication {

    /**
     * 注入 RestTemplate
     * 并用 @LoadBalanced 注解，用负载均衡策略请求服务提供者
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(SingleCustomerApplication.class, args);
    }
}
