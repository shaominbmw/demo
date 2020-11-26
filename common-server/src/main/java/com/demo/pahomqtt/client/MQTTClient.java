package com.demo.pahomqtt.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName MQTTClient
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-21 13:47
 **/
@Component
public class MQTTClient {

    static String HOST = "tcp://47.99.130.104:1883";
    static String username = "admin";
    static String password = "public";
    static String clientId = String.valueOf("wuye" + System.currentTimeMillis());
    static String TOPIC = "test/aaa";

    private static MqttClient client = null;

    @Autowired
    PushCallback pushCallback;

    // 连接mqtt服务器

//    @PostConstruct
//    public void init() {
//        connect(HOST, clientId, username, password);
//        subscribe(TOPIC, 0);
//        System.out.println("mq连接成功");
//    }

    public void connect(String HOST, String clientId, String username, String password) {
        try {
            if (client == null) {
                // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
                client = new MqttClient(HOST, clientId, new MemoryPersistence());
                // 设置回调
                client.setCallback(pushCallback);
            }
            MqttConnectOptions options = getOption(username, password);
            if (!client.isConnected()) {
                client.connect(options);
//    	    		logger.info("客户端成功建立连接。。。");
            } else {//这里的逻辑是如果连接成功就重新连接
                client.disconnect();
                client.connect(options);
//    	    		logger.info("客户端成功建立连接。。。");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void subscribe(String TOPIC, int qos) {
        try {
            client.subscribe(TOPIC, qos);
//    			logger.info("客户端成功订阅。。。");
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    private MqttConnectOptions getOption(String username, String password) {
        // MQTT的连接设置
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(false);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        //设置断开后重新连接
        options.setAutomaticReconnect(false);
        return options;
    }

    // 重新链接
    public void reconnect() {
        try {
            client.reconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
