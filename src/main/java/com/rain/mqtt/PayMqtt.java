package com.rain.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Arrays;

/**
 * 基于MQTT协议给MQTT服务器发送支付通知, MQTT服务器将支付通知转发给相应的设备
 *
 * 需要考虑的问题
 * 1.如何知道是哪一个设备, 即哪一个按摩椅
 * 2.如何设置Message Type
 * 3.payload以什么样的形式编辑放进去
 *
 * @author sourc
 * @date 2018/7/22
 */
public class PayMqtt {
    /**
     * tcp://MQTT安装的服务器地址:MQTT定义的端口号
     */
    private static final String HOST = "tcp://123.57.222.229:1883";
    /**
     * 主题
     */
    private static final String TOPIC = "/v1/device/868575023736996/deveventreq/noncestr";
    /**
     * 客户端标识, 可使用设备号
     */
    private static final String CLIENT_ID = "shared";

    /**
     * 与MQTT服务器通讯的客户端
     */
    private MqttClient client;
    /**
     * 主题
     */
    private MqttTopic topic;
    /**
     * 认证用户信息
     */
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    private static final byte[] CONTENTS = new byte[22];

    static {
        // TODO 处理PayLoad, 最前面的两位是指令54, Broker接收到消息之后, 会组包, FixHeader
        // TODO 完善的位置 被占用椅子的不能扫两次
        // TODO 完善的位置 椅子的状态, 是否离线, 如此在用户支付之前爆出椅子不可以使用
        CONTENTS[0] = 0x54;

        // 产品类型
        CONTENTS[1] = 0x00;

        // 支付金额
        CONTENTS[2] = 0x00;
        CONTENTS[3] = 0x00;
        CONTENTS[4] = 0x00;
        CONTENTS[5] = 0x64;

        // 工作时长
        CONTENTS[6] = 0x00;
        CONTENTS[7] = 0x00;
        CONTENTS[8] = 0x00;
        CONTENTS[9] = 0x05;

        // 高电平时长
        CONTENTS[10] = 0x00;
        CONTENTS[11] = 0x00;
        CONTENTS[12] = 0x00;
        CONTENTS[13] = 0x00;

        // 低电平时长
        CONTENTS[14] = 0x00;
        CONTENTS[15] = 0x00;
        CONTENTS[16] = 0x00;
        CONTENTS[17] = 0x00;

        // 脉冲个数
        CONTENTS[18] = 0x00;
        CONTENTS[19] = 0x00;
        CONTENTS[20] = 0x00;
        CONTENTS[21] = 0x00;
    }

    /**
     * 给服务器传输的具体消息
     */
    private MqttMessage message;

    public PayMqtt() throws MqttException {
        client = new MqttClient(HOST, CLIENT_ID, new MemoryPersistence());
        connect();
    }

    /**
     * 连接MQTT服务器
     */
    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            // 回调方法
            client.setCallback(new PublishCallback());
            client.connect(options);
            topic = client.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布应用消息
     *
     * @param topic   话题
     * @param message 消息
     * @throws MqttPersistenceException 异常
     * @throws MqttException            异常
     */
    private void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("应用消息发布完成" + token.isComplete());
    }

    public void start() {
        try {
            PayMqtt payMqtt = new PayMqtt();
            payMqtt.message = new MqttMessage();

            // 保证消息能到达一次
            payMqtt.message.setQos(1);
            payMqtt.message.setRetained(true);

            payMqtt.message.setPayload(CONTENTS);
            payMqtt.publish(payMqtt.topic, payMqtt.message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class PublishCallback implements MqttCallback {
        @Override
        public void connectionLost(Throwable cause) {
            System.out.println("连接断开，可以做重连");
            cause.printStackTrace();
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            System.out.println("数据已发送到MQTT服务器, 数据传输结束 Token Status" + token.isComplete());
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            System.out.println("接收消息主题: " + topic);
            System.out.println("接收消息Qos: " + message.getQos());
            System.out.println("接收消息内容: " + new String(message.getPayload()));
        }
    }

    public static void main(String[] args) {
        System.out.println(Integer.toHexString(84));
        System.out.println(Arrays.toString(Integer.toHexString(84).getBytes()));
    }
}