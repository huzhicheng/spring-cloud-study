package kite.springcloud.common.stream;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * MessageUtil
 *
 * @author fengzheng
 * @date 2018/12/20
 */
public class MessageUtil {

    public static <T> Message<T> message(T message){
        return MessageBuilder.withPayload(message).build();
    }

}
