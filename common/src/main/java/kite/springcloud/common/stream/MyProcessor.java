package kite.springcloud.common.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * MyProcessor
 *
 * @author fengzheng
 * @date 2018/12/20
 */
@Component
public interface MyProcessor {

    String MESSAGE_INPUT = "log_input";

    String MESSAGE_OUTPUT = "log_output";

    String LOG_FORMAT_INPUT = "log_format_input";

    String LOG_FORMAT_OUTPUT = "log_format_output";

    @Input(MESSAGE_INPUT)
    SubscribableChannel logInput();

    @Output(MESSAGE_OUTPUT)
    MessageChannel logOutput();

    @Input(LOG_FORMAT_INPUT)
    SubscribableChannel logFormatInput();

    @Output(LOG_FORMAT_OUTPUT)
    MessageChannel logFormatOutput();

}
