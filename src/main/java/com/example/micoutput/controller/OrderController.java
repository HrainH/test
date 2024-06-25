package com.example.micoutput.controller;

import com.example.micoutput.entity.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.Jedis;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class OrderController {
    /**
     * 预定晚餐
     * @param name
     */
    public void dinnerOrder(String name){
        if(timeCheck(18)){//午餐成功处理，即12点之前
            List<Order> orders=getOrdersWithName(name);
            int num=0;
            for(Order order:orders){
                if(dateCheck(order.getTime())&order.getType().equals("晚餐")&order.getState().equals("成功")){//如果存在一个成功晚餐与当前日期相同
                    num++;
                }
            }
            if(num==0){
                List<Order> orderList=getOrdersWithName(name);
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedCurrentDateTime = currentDateTime.format(formatter);
                Order order=new Order(orderList.size()+1,name,formattedCurrentDateTime,"晚餐","成功");
                insert(order);
                System.out.println("预订晚餐成功！");
            }else {
                System.out.println("您已经预订晚餐，请勿重复预订！");
            }
        }else {//午餐失败处理，即12点之后
            System.out.println("当前已经超过18点，不能预订当日晚餐！");
            List<Order> orders=getOrdersWithName(name);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedCurrentDateTime = currentDateTime.format(formatter);
            Order order=new Order(orders.size()+1,name,formattedCurrentDateTime,"晚餐","失败");
            insert(order);
        }
    }

    /**
     * 预定午餐
     * @param name
     */
    public void lunchOrder(String name){
        if(timeCheck(12)){//午餐成功处理，即12点之前
            List<Order> orders=getOrdersWithName(name);
            int num=0;
            for(Order order:orders){
                if(dateCheck(order.getTime())&order.getType().equals("午餐")&order.getState().equals("成功")){//如果存在一个成功午餐与当前日期相同
                    num++;
                }
            }
            if(num==0){
                List<Order> orderList=getOrdersWithName(name);
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedCurrentDateTime = currentDateTime.format(formatter);
                Order order=new Order(orderList.size()+1,name,formattedCurrentDateTime,"午餐","成功");
                insert(order);
                System.out.println("预订午餐成功！");
            }else {
                System.out.println("您已经预订午餐，请勿重复预订！");
            }
        }else {//午餐失败处理，即12点之后
            System.out.println("当前已经超过12点，不能预订当日午餐！");
            List<Order> orders=getOrdersWithName(name);
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedCurrentDateTime = currentDateTime.format(formatter);
            Order order=new Order(orders.size()+1,name,formattedCurrentDateTime,"午餐","失败");
            insert(order);
        }
    }

    /**
     * 查询历史订单
     * @param name
     */
    public void findOrder(String name){
        System.out.println("---------------历史订单-----------------");
        List<Order> orderList=getOrdersWithName(name);
        if(!orderList.isEmpty()) {
            System.out.println("id     时间                    类型     状态");
            for (Order order : orderList) {
                if (order.getName().equals(name)) {
                    System.out.println(order);
                }
            }
        }else{
            System.out.println("当前用户无订单！");
        }
    }
/*-----------------------------------------------以下为方法函数----------------------------------------------*/

    /**
     * 添加订单到redis
     * @param order
     */
    public void insert(Order order){
        Jedis jedis = new Jedis("localhost", 6379);
        List<Order> orderList=getOrders();
        orderList.add(order);
        JSONArray jsonArray=new JSONArray(orderList);
        String mes=jsonArray.toString();
        jedis.set("order", mes);
        jedis.close();
    }

    /**
     * 根据用户姓名查询订单
     * @param name
     * @return
     */
    public List<Order> getOrdersWithName(String name){
        List<Order> temp=getOrders();
        List<Order> ordersWithName= new ArrayList<>();
        if(!temp.isEmpty()) {
            for (Order order : temp) {
                if (order.getName().equals(name)) {
                    ordersWithName.add(order);
                }
            }
        }
        return ordersWithName;
    }
    /**
     * 判断当前时间是否超过指定时间点
     * @param cTime
     * @return
     */
    public boolean timeCheck(int cTime){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedCurrentDateTime = currentDateTime.format(formatter);
        System.out.println("当前时间: " + formattedCurrentDateTime);
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalTime noon = LocalTime.of(cTime, 0);
        if (currentTime.isAfter(noon)) {
//            System.out.println("当前时间已经超过"+cTime+"点");
            return false;
        } else {
//            System.out.println("当前时间还没有超过"+cTime+"点");
            return true;
        }
    }

    /**
     * 获取当前redis的order信息
     * @return
     */
    public List<Order> getOrders(){
        Jedis jedis =new Jedis("localhost", 6379);
        String mes=jedis.get("order");
        List<Order> orderList=new ArrayList<>();
        try{
            JSONArray jsonArray=new JSONArray(mes);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                String time = jsonObject.getString("time");
                String type = jsonObject.getString("type");
                String state = jsonObject.getString("state");
                Order order = new Order(id, name, time, type, state);
                orderList.add(order);
            }
            jedis.close();
            return orderList;
        }catch (Exception e){
            //System.out.println("Q:"+e);
            return orderList;
        }
    }

    /**
     * 用于判断当前订餐时间是否在同一天
     * @param dateTimeString
     * @return
     */
    public boolean dateCheck(String dateTimeString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            // 解析时间字符串为LocalDateTime
            LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeString, formatter);
            // 获取日期部分
            LocalDate parsedDate = parsedDateTime.toLocalDate();
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            // 打印解析的日期和当前日期
//            System.out.println("解析的日期: " + parsedDate);
//            System.out.println("当前日期: " + currentDate);
            // 比较解析的日期和当前日期
            if (parsedDate.isEqual(currentDate)) {
//                System.out.println("解析的日期与当前日期相同");
                return true;
            }  else {
//                System.out.println("解析的日期晚于当前日期");
                return false;
            }
        } catch (DateTimeParseException e) {
            System.err.println("日期时间解析错误: " + e.getMessage());
            return false;
        }
    }
}
