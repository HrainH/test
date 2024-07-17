package com.example.kafkademo;

import jakarta.annotation.Resource;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducer {

    @Resource
    private StreamBridge streamBridge;

    @RequestMapping("kafka")
    public Boolean sendMessageToKafka(String msg){
        return streamBridge.send("output-out-0", MessageBuilder.withPayload("kafka测试："+msg).build());
    }

}
