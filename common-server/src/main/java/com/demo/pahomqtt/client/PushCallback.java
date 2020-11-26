package com.demo.pahomqtt.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName PushCallback
 * @Author Shaomin
 * @Description TODO
 * @Date 2020-11-21 13:54
 **/
@Component
public class PushCallback implements MqttCallback {

@Autowired
    MQTTClient mqttClient;

    @Override
    public void connectionLost(Throwable throwable) {
        mqttClient.reconnect();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println(new String(mqttMessage.getPayload()));

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
