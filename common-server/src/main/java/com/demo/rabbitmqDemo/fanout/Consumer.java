package com.demo.rabbitmqDemo.fanout;

import com.demo.rabbitmqDemo.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName Consumer
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-11 16:00
 **/
public class Consumer {

    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //创建临时队列
        String queue = channel.queueDeclare().getQueue();
        //声明交换机  参数1 交换机名称 2 交换机的类型
        channel.exchangeDeclare("hello", "fanout");
        //绑定队列参数1 队列名称 参数2 交换机名称 参数3 routingkey(广播模式key可以省略)
        channel.queueBind(queue, "hello", "");
        //接受消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });

    }
}
