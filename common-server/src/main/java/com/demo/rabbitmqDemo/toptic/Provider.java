package com.demo.rabbitmqDemo.toptic;

import com.demo.rabbitmqDemo.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ClassName Provider
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-12 9:05
 **/
public class Provider {

    public static void main(String[] args) throws IOException {
        //创建连接
        Connection connection = RabbitMQUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare("topics", "topic");
        String routingKey = "user.name";
        //发送教习
        channel.basicPublish("topics", routingKey, null, "HelloWord".getBytes());
        //关闭连接和通道
        RabbitMQUtils.closeConnection(connection, channel);
    }
}
