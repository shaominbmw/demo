package com.demo.rabbitmqDemo.spring;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName RouteConsumer
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-12 11:19
 **/
@Component
public class RouteConsumer {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "routes", type = "direct"),
                    key = {"info", "error"}
            )
    })
    public void receive1(String message) {

        System.out.println("message = " + message);

    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = "routes", type = "direct"),
                    key = {"info"}
            )
    })
    public void receive2(String message) {

        System.out.println("message 2= " + message);

    }

}
