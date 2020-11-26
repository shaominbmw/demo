package com.demo.pahomqtt.publish;

import com.demo.pahomqtt.client.PushCallback;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class SendMQTT {
    // 客户端
    private static MqttClient client;
    private static MqttTopic topic11;
    static String TOPIC = "test/bbb";

    // 创建链接
    public static void connect(String HOST, String clientId, String username, String password) {
        try {
            // MemoryPersistence设置clientid的保存形式，默认为以内存保存
            if (client == null) {
                synchronized (MqttClient.class) {
                    if (client == null) {
                        // 建立链接，mqttConfig.getHost()获得服务器地址，mqttConfig.getSendId()获取clientId,客户端唯一标示，new MemoryPersistence()设置clientId的保存形式，默认为以内存保存
                        client = new MqttClient(HOST, clientId, new MemoryPersistence());
                        // 设置回调类，后面会有详细说明
                        client.setCallback(new PushCallback());
                    }
                }
            }
            MqttConnectOptions options = getOption(username, password);
//            client.connect(options);
            if (!client.isConnected()) {
                client.connect(options);
//    	    		logger.info("客户端成功建立连接。。。");
            } else {//这里的逻辑是如果连接成功就重新连接
                client.disconnect();
                client.connect(options);
//    	    		logger.info("客户端成功建立连接。。。");
            }
//			logger.info("成功链接服务器。。。");
            topic11 = client.getTopic(TOPIC);
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 推送消息
     *
     * @throws MqttPersistenceException
     * @throws MqttException
     */
    public static void publish(String body)
            throws MqttException {
        MqttMessage message = new MqttMessage();
        message.setQos(1);// 消息质量
        message.setRetained(true);// 断开链接是否保存消息，true保存
        message.setPayload(body.getBytes());
        MqttDeliveryToken token = topic11.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! "
                + token.isComplete());
    }

    // 重连
    public static void reconnect() {
        try {
            client.reconnect();
        } catch (MqttException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static MqttConnectOptions getOption(String username, String password) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        return options;
    }
}
