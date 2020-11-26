package com.demo.rabbitmqDemo.work;

import com.demo.rabbitmqDemo.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ClassName Provider
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-12 10:40
 **/
public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明队列  参数1 队列名称  参数2 是否持久化
         channel.queueDeclare("work", false, false, false, null);
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", "work", null,( "work发送的消息"+i).getBytes());
        }
        RabbitMQUtils.closeConnection(connection, channel);


    }
}
