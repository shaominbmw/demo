package com.demo.weichuan;


import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ReportMqtt implements MqttCallback {
//        public String HOST = "tcp://gateway.mqtt.iot.vision.uplink.smartkit.io:1885";
//    public String TOPIC = "application/0000000000000002/node/+/rx";
//    private String name = "wangw";
//    private String passWord = "ww1030";
    static String HOST = "tcp://47.99.130.104:1883";
    static String name = "admin";
    static String passWord = "public";
    static String TOPIC = "gateway/#";

    private MqttClient client;
    private MqttConnectOptions options;

    @Autowired
    private LoraService loraService;

    //clientId不能重复所以这里我设置为系统时间
    String clientid = String.valueOf(System.currentTimeMillis());

    //    MessageInfo info =new MessageInfo();
//    @PostConstruct
//    public void result() {
//        try {
//            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
//            client = new MqttClient(HOST, clientid, new MemoryPersistence());
//            // MQTT的连接设置
//            options = new MqttConnectOptions();
//            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
//            options.setCleanSession(false);
//            // 设置连接的用户名
//            options.setUserName(name);
//            // 设置连接的密码
//            options.setPassword(passWord.toCharArray());
//            // 设置超时时间 单位为秒
//            options.setConnectionTimeout(10);
//            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
//            options.setKeepAliveInterval(3600);
//            options.setAutomaticReconnect(true);
//            // 设置回调
//            client.setCallback(this);
//            client.connect(options);
//            //订阅消息
//            int[] Qos = {0};
//            String[] topic1 = {TOPIC};
//            client.subscribe(topic1, Qos);
//
//        } catch (Exception e) {
//        }
//
//    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {

        try {
            String msg = new String(message.getPayload());
            System.out.println(msg);
            loraService.messageArrived(topic, message);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//        log.info("消息发送成功");
    }
}