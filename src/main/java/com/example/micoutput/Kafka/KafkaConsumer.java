package com.example.micoutput.Kafka;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    @KafkaListener(topics = {KafkaProducer.TOPIC},groupId = "group1")
    public void testConsumer(String body , Acknowledgment acknowledgment) {
        log.info("topic: {}, 消费消息内容: {}", KafkaProducer.TOPIC, body);
        acknowledgment.acknowledge();//手动提交消费消息
    }
}
