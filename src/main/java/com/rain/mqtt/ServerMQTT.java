package com.rain.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
/**
 * @Author:huhy
 * @DATE:Created on 2017/12/1 14:29
 * @Modified By:
 * @Class Description:
 */
public class ServerMQTT {

    //tcp://MQTT安装的服务器地址:MQTT定义的端口号
    public static final String HOST = "tcp://39.106.101.209:1883";
    //定义一个主题
    public static final String TOPIC = "pay";
    //定义MQTT的ID，可以在MQTT服务配置中指定
    private static final String clientid = "server";

    private MqttClient client;
    private MqttTopic topic11;
    private String userName = "admin";  //非必须
    private String passWord = "password";  //非必须

    private MqttMessage message;

    /**
     * 构造函数
     * @throws MqttException
     */
    public ServerMQTT() throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(HOST, clientid, new MemoryPersistence());
        connect();
    }

    /**
     *  用来连接服务器
     */
    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(new PushCallback());
            client.connect(options);

            topic11 = client.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param topic
     * @param message
     * @throws MqttPersistenceException
     * @throws MqttException
     */
    public void publish(MqttTopic topic , MqttMessage message) throws MqttPersistenceException,
            MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! "
                + token.isComplete());
    }

    /**
     *  启动入口
     * @param args
     * @throws MqttException
     */
    public static void main(String[] args) throws MqttException, InterruptedException {
        ServerMQTT server = new ServerMQTT();
        server.message = new MqttMessage();
        server.message.setQos(1);  //保证消息能到达一次
        server.message.setRetained(true);
        server.message.setPayload("010203".getBytes());
        server.publish(server.topic11 , server.message);
//        server.message.setPayload("abcde1".getBytes());
//        server.publish(server.topic11 , server.message);
//        Thread.sleep(2000);
//        server.message.setPayload("abcde2".getBytes());
//        server.publish(server.topic11 , server.message);
//        Thread.sleep(2000);
//        server.message.setPayload("abcde3".getBytes());
//        server.publish(server.topic11 , server.message);
//        System.out.println(server.message.isRetained() + "------ratained状态");


    }
}