package com.chenk.mqttdemo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


/**
 * @Author chenk
 * @create 2020/9/8 11:13
 */
public class MyProducer {

    private static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";

    public static void main(String[] args) throws JMSException {
        // 创建连接工厂
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 打开连接
        connection.start();
        // 创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建队列目标,并标识队列名称，消费者根据队列名称接收数据
        Destination destination = session.createTopic("myQueue");
        // 创建一个生产者
        MessageProducer producer = session.createProducer(destination);
        // 向队列推送10个文本消息数据
        for (int i = 1; i <= 10; i++) {
            // 创建文本消息
            TextMessage message = session.createTextMessage("第" + i + "个文本消息");
            //发送消息
            producer.send(message);
            //在本地打印消息
            System.out.println("已发送的消息：" + message.getText());
        }
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

}

