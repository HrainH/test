package com.example.micoutput.RabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@RabbitListener(queues = "topic.order")
@Component
public class RabbitMQConsumer {
    @RabbitHandler
    public void process(Map<?,?> Message) {
        System.out.println(" RabbitMQConsumer消费者收到消息  : " + Message);
    }
}
