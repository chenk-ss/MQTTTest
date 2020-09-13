package com.chenk.mq_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

/**
 * @Author chenk
 * @create 2020/9/13 17:51
 */
@Component
public class Queue_Producer {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue, "****:" + UUID.randomUUID().toString().substring(0, 6));
    }

    @Scheduled(fixedDelay = 3000)
    public void produceMsgScheduled(){
        jmsMessagingTemplate.convertAndSend(queue, "****produceMsgScheduled:" + UUID.randomUUID().toString().substring(0, 6));
        System.out.println("****produceMsgScheduled  send******");
    }
}
