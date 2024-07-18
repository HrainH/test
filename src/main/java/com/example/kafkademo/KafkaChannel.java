package com.example.kafkademo;


import org.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

//消费组接受消息的通道设置
@Configuration
public class KafkaChannel {

    @Bean
    public Consumer<Message<String>> input(){
        return message -> {
            System.out.println("接收到消息：" + message.getPayload());
            JSONObject jsonObject = new JSONObject(message.getPayload());

            String title = jsonObject.getString("title");
            String content = jsonObject.getString("content");

            System.out.println(title+" "+content);

//            System.out.println("接收到消息：" + message.getHeaders());
        };
    }
}
