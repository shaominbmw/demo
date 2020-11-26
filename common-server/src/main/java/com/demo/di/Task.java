package com.demo.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPattern;

/**
 * @ClassName Task
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-24 16:00
 **/

@EnableScheduling
@Component
public class Task {


    Test test;

    private void set(Test test) {
        this.test = test;
    }


}
