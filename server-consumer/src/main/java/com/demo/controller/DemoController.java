package com.demo.controller;

import com.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    StaffService staffService;

    @RequestMapping("/demo")
    public void demo(){
         staffService.add(1);
    }
}
