package com.chenk.mq_producer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class MqProducerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private Queue_Producer queue_producer;

    @Test
    public void testSend() throws Exception{
        queue_producer.produceMsg();
    }

}
