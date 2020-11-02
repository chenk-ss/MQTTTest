package com.chenk.mqttdemo;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/**
 * @Author chenk
 * @create 2020/9/12 16:05
 */
public class Producer {

    private String username = "admin";
    private String password = "password";
    private MqttClient client;
    private MqttTopic topic;

    private String TOPIC;

    //
    private void connect() {
        // new mqttConnection 用来设置一些连接的属性
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
        // 换而言之，设置为false时可以客户端可以接受离线消息
        options.setCleanSession(false);
        // 设置连接的用户名和密码
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            // 设置回调类
//            client.setCallback(new PushCallback());
            // 连接
            client.connect(options);
            // 获取activeMQ上名为TOPIC的topic
            topic = client.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
