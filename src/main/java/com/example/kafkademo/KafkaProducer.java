package com.example.kafkademo;

import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducer {

    @Resource
    private StreamBridge streamBridge;

    @RequestMapping("kafka")
    public Boolean sendMessageToKafka(@RequestBody Msg msg){
        String jsonString = JSONUtil.toJsonStr(msg);
        System.err.println(jsonString);
        return streamBridge.send("output-out-0", MessageBuilder.withPayload(jsonString).build());
    }

}
