package com.chenk.mqttdemo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @Author chenk
 * @create 2020/9/8 13:54
 */
public class MyConsumer {
    private static final String ACTIVEMQ_URL = "tcp://127.0.0.1:61616";

    public static void main(String[] args) throws JMSException, IOException {
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
        // 创建消费者
        MessageConsumer consumer = session.createConsumer(destination);
        // 创建消费的监听
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                if (message instanceof BytesMessage) {
                    BytesMessage bm = (BytesMessage) message;
                    byte[] bys = null;
                    try {
                        bys = new byte[(int) bm.getBodyLength()];
                        bm.readBytes(bys);
                        System.out.println("消费的消息1：" + new String(bys));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                } else {
                    TextMessage bm = (TextMessage) message;
                    try {
                        System.out.println("消费的消息2：" + bm.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
