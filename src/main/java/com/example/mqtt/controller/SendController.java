package com.example.mqtt.controller;

import com.example.mqtt.service.IMqttSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 *对外提供消息发布服务
 *@description:
 *@author: x.zaft
 *@Date 2020/8/15
 *@Version V1.0
 */
@RestController
@RequestMapping("/send")
public class SendController {

    @Autowired
    private IMqttSender iMqttSender;

    @GetMapping("/message")
    public void message(String data){
        iMqttSender.sendToMqtt("iot/mqtt/the/first", data);
    }
}
