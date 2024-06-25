package com.example.micoutput.Kafka;

import cn.hutool.json.JSONUtil;
import com.example.micoutput.controller.FeignUserServe;
import com.example.micoutput.controller.OrderController;
import com.example.micoutput.entity.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.RestController;


import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class KafkaProducer {
    private final FeignUserServe feignUserServe;
    private final OrderController orderController;

    @Autowired
    public KafkaProducer(FeignUserServe feignUserServe, OrderController orderController) {
        this.feignUserServe = feignUserServe;
        this.orderController = orderController;
    }

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    //自定义topic
    public static final String TOPIC = "ordering";

    public void entrance() {
        while (true) {
            System.out.println("""
                    -----------欢迎登录订餐管理系统-----------
                    请输入您的姓名(0退出)：
                    """);
            String name=null;
            while (!Objects.equals(name, "0")) {
                Scanner scanner = new Scanner(System.in);
                name = scanner.next();
                if(Objects.equals(name, "0")){
                    System.exit(0);
                }
                if (feignUserServe.find(name) == 1) {
                    while(true){
                        System.out.println("-----------欢迎使用订餐管理系统-----------\n" +
                                "姓名："+name);
                        System.out.println("""
                                1：查询订单
                                2：午餐预订
                                3、晚餐预订
                                4、退出
                                请输入您的选择：
                                """);
                        String choice;
                        Scanner scanner1 = new Scanner(System.in);
                        choice=getChoice(scanner1.next());
                        switch(choice){
                            case "1":
                                orderController.findOrder(name);
                                break;
                            case "2":
                                orderController.lunchOrder(name);
                                break;
                            case "3":
                                orderController.dinnerOrder(name);
                                break;
                            case "4":
                                System.exit(0);
                        }
                    }
                }else{
                    System.out.println("用户不存在，请重新输入！");
                }
            }
        }
    }

    public String getChoice(String choice){
        Date time = new Date();
        Command command=new Command();
        command.setSNum(choice);
        command.setOCmd(choice);
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(KafkaProducer.TOPIC, JSONUtil.toJsonStr(command));
        future.handleAsync((result, ex)->{
            if (result != null) {
                //success
                log.info("{}-生产者发送消息成功:{},时间:{}",KafkaProducer.TOPIC, result, time);
            } else if(ex != null){
                //failure
                log.error("{}-生产者发送消息失败:{}",KafkaProducer.TOPIC,ex.getMessage());
            }
            return null;
        });
        return choice;
    }

}
