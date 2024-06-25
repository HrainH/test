package com.example.micoutput.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class Command {
    private String sNum;//序号
    private String oCmd;//命令
    public void setOCmd(String sNum){
        if(Objects.equals(sNum,"1")){
            this.oCmd="查询订单";
        }else if(Objects.equals(sNum,"2")){
            this.oCmd="午餐预订";
        }else if(Objects.equals(sNum,"3")){
            this.oCmd="晚餐预订";
        }else if(Objects.equals(sNum,"4")){
            this.oCmd="退出";
        }else {
            this.oCmd="未知命令";
        }
    }
}
