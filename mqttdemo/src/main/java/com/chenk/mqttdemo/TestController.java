package com.chenk.mqttdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author chenk
 * @create 2020/9/7 10:41
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MqttGateway mqttGateway;

    @RequestMapping("/sendMqtt.do")
    public String sendMqtt(String sendData, String topic) {
        mqttGateway.sendToMqtt(sendData, topic);
        return "OK";
    }

}
