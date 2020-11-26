package com.demo.entity;

import com.demo.util.TimeUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName User
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-13 15:54
 **/
//@ConfigurationProperties(prefix = "user")


@Data
public class User {
    private String name;
    private int age;



}
