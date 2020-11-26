package com.demo.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @ClassName Person
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-09 13:56
 **/
@Data

public class Person {

    private String name;

    private String age;

}
