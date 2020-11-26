package com.demo.controller;

import com.demo.util.RedisUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName Demo
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-06 14:17
 **/
@RestController

public class Demo {


    @Autowired
    RedisUtils redisUtils;

    final String key = "aaaa";


    @PostMapping("run")
    public void run(@RequestParam Map<String, String> map) {
        System.out.println("person = " + map);

    }

    @GetMapping("gg")
    public void run(@RequestParam("name") String s) {
        System.out.println("person = " + s);

    }




    @Test
    public void main() {

    }
}


