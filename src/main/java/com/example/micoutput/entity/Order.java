package com.example.micoutput.entity;

import lombok.Data;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

@Data
public class Order {
    private int id;
    private String name;
    private String time;
    private String type;
    private String state;

    public Order(int id, String name, String time, String type, String state) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.type=type;
        this.state = state;
    }

    public Order(){}

    @Override
    public String toString(){
        return id+"      "+time+"    "+type+"     "+state;
    }

//    redis中的对象字符串转对象
//    public static Order fromCustomString(String customString) {
//        String regex = "Order\\(id=(\\d+), name=(null|[^,]+), time=(null|[^,]+), state=(null|[^)]+)\\)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(customString);
//        if (matcher.find()) {
//            int id = Integer.parseInt(matcher.group(1));
//            String name = matcher.group(2).equals("null") ? null : matcher.group(2);
//            String time = matcher.group(3).equals("null") ? null : matcher.group(3);
//            String state = matcher.group(4).equals("null") ? null : matcher.group(4);
//            return new Order(id, name, time, state);
//        }
//        throw new IllegalArgumentException("Invalid order string: " + customString);
//    }
}
