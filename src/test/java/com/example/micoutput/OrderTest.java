package com.example.micoutput;

import com.example.micoutput.controller.FeignUserServe;
import com.example.micoutput.controller.OrderController;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderTest extends MicOutputApplicationTests{

    @Autowired
    private FeignUserServe feignUserServe;
    @Autowired
    private OrderController orderController;

    @Test
    public void nameFindTest(){
        Assert.assertSame("用户不存在，请重新输入！",1,feignUserServe.find("张三"));
        Assert.assertSame("用户不存在，请重新输入！",1,feignUserServe.find("王五"));
    }

    @Test
    public void orderFindTest(){
        System.out.println(orderController.getOrders());
        Assert.assertSame("当前用户订单有误！",2,orderController.getOrdersWithName("张三").size());
        Assert.assertSame("当前用户无订单！",1,orderController.getOrdersWithName("小梅").size());
    }

    @Test
    public void lunchOrderAddTest(){
        orderController.lunchOrder("张三");
        System.out.println(orderController.getOrdersWithName("张三"));
    }

    @Test
    public void dinnerOrderAddTest(){
        orderController.dinnerOrder("张三");
        System.out.println(orderController.getOrdersWithName("张三"));
    }

}
