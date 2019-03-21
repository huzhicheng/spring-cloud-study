package kite.springcloud.stream.rabbit.customer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Component;

/**
 * DefaultMessageListener
 *
 * @author fengzheng
 * @date 2018/12/25
 */
@Slf4j
@Component
public class DefaultMessageListener {

    @StreamListener(Processor.INPUT)
    public void processMyMessage(String message) {
        log.info("接收到消息：" + message);
    }

}
