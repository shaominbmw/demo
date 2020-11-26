package com.demo.rabbitmqDemo.spring;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName Provider
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-12 10:12
 **/
@Component
    @RabbitListener(queuesToDeclare = @Queue)
public class WorkConsumer {

    @RabbitHandler
    public void receive1(String message, Channel channel) {
        System.out.println("message = " + message);

    }

//    @RabbitListener(queuesToDeclare = @Queue)
//    public void receive2(String message) {
//        System.out.println("message2 = " + message);
//    }
}
