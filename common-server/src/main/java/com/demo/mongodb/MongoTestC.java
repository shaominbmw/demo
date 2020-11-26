package com.demo.mongodb;

import com.alibaba.fastjson.JSON;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MongoTestC {

    @Autowired
    private MongoTestDao mtdao;

    @GetMapping(value = "/test1")
    public void saveTest() throws Exception {
        MongoTest mgtest = new MongoTest();
        mgtest.setId(11);
        mgtest.setAge(33);
        mgtest.setName("ceshi");
        mtdao.saveTest(mgtest);
    }

    @GetMapping(value = "/test2")
    public List<User> findTestByName() {
        List<User> mgtest = mtdao.findTestByName("小明");
        System.out.println("mgtest is " + mgtest);
        return mgtest;
    }

    @GetMapping(value = "/test3")
    public void updateTest() {
        MongoTest mgtest = new MongoTest();
        mgtest.setId(11);
        mgtest.setAge(44);
        mgtest.setName("ceshi2");
        mtdao.updateTest(mgtest);
    }

    @GetMapping(value = "/test4")
    public void deleteTestById() {
        mtdao.deleteTestById(11);
    }

    @PostMapping(value = "/test5")
    public void save(@RequestBody Map<String,Object>  s) {
        String s1 = JSON.toJSONString(s);
        mtdao.save(s1);
        System.out.println("s1 = " + s1);
        System.out.println("map = " + s);
    }
}