package com.example.micoutput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

@RestController
public class FeignUserServe {
    @Autowired
    private UserServe userServe;

    @RequestMapping("find")
    public int find(String name){
        try {
            if (!userServe.findsG().isEmpty()) {
                JSONArray jsonArray = new JSONArray(userServe.findsG().get("data").toString().replace("=", ":"));
                int num = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    String temp = obj.getString("name");
                    if (Objects.equals(temp, name)) {
                        num++;
                    }
                }
                if (num == 1) {
                    return 1;
                }
            }
        }catch (Exception e){
            System.out.println("请检查网关是否开启");
        }
        return 0;
    }
}
