package com.demo.rabbitmqDemo.toptic;

import com.demo.rabbitmqDemo.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName Consumer
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-12 9:07
 **/
public class Consumer {

    public static void main(String[] args) throws IOException {
        //创建连接
        Connection connection = RabbitMQUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare("topics", "topic");
        //创建临时队列
        String queue = channel.queueDeclare().getQueue();
        String routingKey = "user.#";
        //绑定队列和交换机和动态routingkey
        channel.queueBind(queue, "topics", routingKey);
        //消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });
    }
}
