package com.example.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yakamy
 *         2017-06-20 17:13
 */
public class MqttConnect {

    private String clientid = "test";

    private MqttClient mqttClient = null;

    public MqttClient mqttConnect(){
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            //设置用户名密码
            options.setUserName(clientid);
            options.setPassword("password".toCharArray());
            // 设置超时时间 单位为秒
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*设置时间 向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(60);
            //连接超时设置
            options.setConnectionTimeout(40);


            MqttClient client = new MqttClient("tcp://127.0.0.1:1883", clientid);
            client.connect(options);
            client.setCallback(new MqttReciever());
            System.out.println(">>>>>>>>>>>>>>>>>> clientId:" + client.getClientId());
            return client;
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public void publish(String data){
        try {
            MqttMessage message = new MqttMessage();
            message.setQos(1);
            message.setRetained(false);
            message.setPayload(data.getBytes("UTF-8"));
            mqttClient.publish("VirtualTopic/dmf/" + clientid, message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(){
        try {
            mqttClient.subscribe("cmf/" + clientid, 1);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*String result = "{\"result\":\"mqtt02=tcp://101.200.150.48:1902\"}".replaceAll(".*\"result\":\"(.*?)\".*", "$1").trim();
        System.out.println(result);*/
        MqttConnect connect = new MqttConnect();
        connect.setMqttClient(connect.mqttConnect());


        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");
        connect.subscribe();
        while (1 == 1) {
            connect.publish(format.format(new Date()));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
