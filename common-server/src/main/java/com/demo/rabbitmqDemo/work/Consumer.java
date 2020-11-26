package com.demo.rabbitmqDemo.work;

import com.demo.rabbitmqDemo.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @ClassName Consumer
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-12 10:41
 **/
public class Consumer {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work", false, false, false, null);
        channel.basicQos(1);
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    System.out.println(new String(body));
                    Thread.sleep(1000);
                    int x = 1 / 0;
                channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    e.printStackTrace();
                    channel.basicNack(envelope.getDeliveryTag(),false ,false );
                }
            }
        });


    }
}
