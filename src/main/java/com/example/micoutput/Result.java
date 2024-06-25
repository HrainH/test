package com.example.micoutput;

import java.util.HashMap;
import java.util.Map;

public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    public Result() {//初始化
        put("code", 200);
        put("msg", "success");
    }
    public static Result error() {//默认错误状况
        return error(500, "未知异常，请联系管理员");
    }
    public static Result error(String msg) {//500的自定义错误状况
        return error(500, msg);
    }
    public static Result error(int code, String msg) {//自定义错误状况
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }
    public static Result ok(String msg) {//自定义成功状况
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }
    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }
    public static Result ok() {
        return new Result();
    }
    public Result put(String key, Object value) {//增加属性
        super.put(key, value);
        return this;
    }
}

