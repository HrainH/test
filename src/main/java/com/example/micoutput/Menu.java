package com.example.micoutput;

import com.example.micoutput.Kafka.KafkaProducer;
import com.example.micoutput.RabbitMQ.RabbitMQProducer;
import com.example.micoutput.controller.OrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
public class Menu {
    private final KafkaProducer kafkaProducer;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public Menu(KafkaProducer kafkaProducer, RabbitMQProducer rabbitMQProducer) {
        this.kafkaProducer=kafkaProducer;
        this.rabbitMQProducer=rabbitMQProducer;
    }

    public void entrance() {
        while (true) {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            // 延迟一段时间后执行任务
            scheduler.schedule(() -> {
                System.out.println("""
                    -----------欢迎来到订餐管理系统-----------
                    请选择想使用的MQ方式(0退出)：
                    1.Kafka
                    2.RabbitMQ
                    """);
            }, 3, TimeUnit.SECONDS);
            String way=null;
            while (!Objects.equals(way,"0")) {
                Scanner scanner = new Scanner(System.in);
                way = scanner.next();
                if(Objects.equals(way, "0")){
                    System.exit(0);
                }
                switch(way){
                    case "1"://kafka
                        kafkaProducer.entrance();
                        break;
                    case "2":
                        //rabbitMq
                        rabbitMQProducer.entrance();
                        break;
                    case "0":
                        System.exit(0);
                }
            }
        }
    }
}
