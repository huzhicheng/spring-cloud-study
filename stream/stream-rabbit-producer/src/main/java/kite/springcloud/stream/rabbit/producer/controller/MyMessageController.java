package kite.springcloud.stream.rabbit.producer.controller;

import kite.springcloud.common.stream.LogInfo;
import kite.springcloud.common.stream.MyProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;

/**
 * MyMessageController
 *
 * @author fengzheng
 * @date 2018/12/14
 */
@Slf4j
@RestController
@EnableBinding(value = {MyProcessor.class})
public class MyMessageController {

    @Autowired
    private MyProcessor myProcessor;

    @GetMapping(value = "sendLogMessage")
    public void sendLogMessage(String message){
        Message<String> stringMessage = org.springframework.messaging.support.MessageBuilder.withPayload(message).build();
        myProcessor.logOutput().send(stringMessage);
    }

    @GetMapping(value = "sendObjectLogMessage")
    public void sendObjectLogMessage(){
        LogInfo logInfo = new LogInfo();
        logInfo.setClientIp("192.168.1.111");
        logInfo.setClientVersion("1.0");
        logInfo.setUserId("198663383837434");
        logInfo.setTime(Date.from(Instant.now()));
        Message<LogInfo> stringMessage = org.springframework.messaging.support.MessageBuilder.withPayload(logInfo).build();
        myProcessor.logOutput().send(stringMessage);
    }
}
