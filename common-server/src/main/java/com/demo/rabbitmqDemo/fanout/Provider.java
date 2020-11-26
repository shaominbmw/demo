package com.demo.rabbitmqDemo.fanout;

import com.demo.rabbitmqDemo.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @ClassName Provider
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-11 15:54
 **/
public class Provider {
    //广播模式消息生产者 声明交换机和交换机类型  不用
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare("hello", "fanout");
        //消息发送
        channel.basicPublish("hello", "", null, "helloword".getBytes());






    }
}
