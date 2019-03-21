package kite.springcloud.stream.rabbit.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;

/**
 * DefaultApplication
 *
 * @author fengzheng
 * @date 2018/12/24
 */
@SpringBootApplication
@EnableBinding(value = {Processor.class})
@Slf4j
public class DefaultApplication {

    public static void main(String[] args) {
        SpringApplication.run(DefaultApplication.class, args);
    }


}
