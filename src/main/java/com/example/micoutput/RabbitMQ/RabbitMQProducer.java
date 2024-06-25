package com.example.micoutput.RabbitMQ;

import cn.hutool.json.JSONUtil;
import com.example.micoutput.controller.FeignUserServe;
import com.example.micoutput.controller.OrderController;
import com.example.micoutput.entity.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@Slf4j
public class RabbitMQProducer {
    private final FeignUserServe feignUserServe;
    private final OrderController orderController;

    @Autowired
    public RabbitMQProducer(FeignUserServe feignUserServe, OrderController orderController) {
        this.feignUserServe = feignUserServe;
        this.orderController = orderController;
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void entrance() {
        while (true) {
            System.out.println("""
                    -----------欢迎登录订餐管理系统-----------
                    请输入您的姓名(0退出)：
                    """);
            String name = null;
            while (!Objects.equals(name, "0")) {
                Scanner scanner = new Scanner(System.in);
                name = scanner.next();
                if (Objects.equals(name, "0")) {
                    System.exit(0);
                }
                if (feignUserServe.find(name) == 1) {
                    while (true) {
                        System.out.println("-----------欢迎使用订餐管理系统-----------\n" +
                                "姓名：" + name);
                        System.out.println("""
                                1：查询订单
                                2：午餐预订
                                3、晚餐预订
                                4、退出
                                请输入您的选择：
                                """);
                        String choice;
                        Scanner scanner1 = new Scanner(System.in);
                        choice = getChoice(scanner1.next());
                        switch (choice) {
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
                } else {
                    System.out.println("用户不存在，请重新输入！");
                }
            }
        }
    }

    public String getChoice(String choice){
        Command command=new Command();
        command.setSNum(choice);
        command.setOCmd(choice);
        String cmdStr= JSONUtil.toJsonStr(command);
        Map<String,Object> map=new HashMap<>();
        map.put("messageData",cmdStr);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("topicExchange", "topic.order", map);
        return choice;
    }
}
