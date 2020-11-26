package com.demo.rabbitmqDemo.routing;

import com.demo.rabbitmqDemo.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ClassName Provider
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-11 15:34
 **/
public class Provider {

    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明交换机  参数1交换机名称，参数2交换机类型
        channel.exchangeDeclare("logs", "direct");
        //路由key 交换机根据key将消息发送到对应消息队列
        String routingKey = "error";
        //消息发送
        channel.basicPublish("logs", routingKey, null, "这是日志消息".getBytes());
        //关闭连接和通道
        RabbitMQUtils.closeConnection(connection, channel);
    }
}
