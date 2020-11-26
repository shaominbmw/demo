package com.demo.rabbitmqDemo.spring;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName TopicConsumer
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-12 13:19
 **/
@Component
public class TopicConsumer {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, exchange = @Exchange(value = "topics", type = "topic"),
                    key = {"user.*", "user.#"}
            )
    })
    public void receive(String message) {
        System.out.println("message = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue, exchange = @Exchange(value = "topics", type = "topic"),
                    key = {"user.*"}
            )
    })
    public void receive2(String message) {
        System.out.println("message2 = " + message);
    }
}
