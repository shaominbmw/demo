package com.demo;

import com.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.List;

/**
 * @ClassName RabbitMQTest
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-12 10:17
 **/

@SpringBootTest(classes = CommonServerApplication.class)
@RunWith(SpringRunner.class)
public class RabbitMQTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void test() {
        User user = new User();
        user.setName("shaomin");
        user.setAge(18);
        Query query=new Query(Criteria.where("name").regex("红富士"));
        List<String> mgt= mongoTemplate.find(query,String.class,"apple");
        System.out.println("mgt = " + mgt);
    }

    @Test
    public void work() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("hello", "hhhahahahha");
        }
    }

    @Test
    public void fanout() {
        rabbitTemplate.convertAndSend("logs", "", "广播模式发送的消息");
    }

    @Test
    public void route() {
        rabbitTemplate.convertAndSend("routes", "error", "来自route的信息");
    }

    @Test
    public void topic() {
        rabbitTemplate.convertAndSend("topics", "user.name.info", "来自topic的信息");
    }


}
