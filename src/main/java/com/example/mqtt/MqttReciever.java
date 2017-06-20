package com.example.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author yakamy
 *         2017-06-20 18:19
 */
public class MqttReciever implements MqttCallback {
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("===================== connection lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<revice message:"+ new String(message.getPayload(), "UTF-8"));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>client:"+ token.getClient().getClientId() + " messageId:"+token.getMessageId());
    }
}
