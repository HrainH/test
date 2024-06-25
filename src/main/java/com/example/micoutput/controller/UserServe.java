package com.example.micoutput.controller;

import com.example.micoutput.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 使用openFeign方式并通过网关掉用用户管理api
 */
@FeignClient(value = "JavaCourseOutput",url = "http://localhost:8080/JavaCourseOutput")
public interface UserServe {
    @RequestMapping("/findG")
    Result findsG();
}
