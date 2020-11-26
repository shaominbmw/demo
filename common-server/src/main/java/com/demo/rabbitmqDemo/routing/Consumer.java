package com.demo.rabbitmqDemo.routing;

import com.demo.rabbitmqDemo.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName Consumer
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-11 15:41
 **/
public class Consumer {
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //获取临时队列
        String queue = channel.queueDeclare().getQueue();
        //路由key
        String routingKey = "error";
        //声明交换机
        channel.exchangeDeclare("logs", "direct");
        //绑定队列和key
        channel.queueBind(queue, "logs", routingKey);
        //接受消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body);
                System.out.println("s = " + s);
            }
        });
    }
}
