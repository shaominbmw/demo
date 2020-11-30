package com.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-26 14:46
 **/
@RestController
@RequestMapping("web/user")
public class UserController {


    @RequestMapping("login")
    public Boolean login(@RequestParam("username")String username,@RequestParam("password")String password){
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        return true;
    }
}
