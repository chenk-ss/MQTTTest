package com.chenk.mq_producer.bean;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

/**
 * @Author chenk
 * @create 2020/9/13 17:38
 */
@Component
@EnableJms
public class ConfigBean {
//    @Value("${myqueue}")
    private String muQueue = "boot-activemq-queue";

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(muQueue);
    }
}
