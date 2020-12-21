package com.example.mqtt.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
/**
 *生产  发布消息接口
 *@description:
 *@author: x.zaft
 *@Date 2020/8/13
 *@Version V1.0
 */
@Component
@MessagingGateway(defaultRequestChannel = MqttProduct.CHANNEL_NAME_OUT)
public interface IMqttSender {
    /**
     * 发送信息到MQTT服务器
     *
     * @param data 发送的文本
     */
    void sendToMqtt(String data);

    /**
     * 发送信息到MQTT服务器
     *
     * @param topic 主题
     * @param data 发送的文本
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic,
                    String data);

    /**
     * 发送信息到MQTT服务器
     * @param topic 主题
     * @param qos 对消息处理的几种机制。
     * 0 至多一次传输，即使消费订阅没有接收到消息也不会再发送，消息会丢失。
     * 1 至少一次传输，会尝试重试，一直到接收到消息，但这种情况可能导致订阅者收到多次重复消息。
     * 2 只有一次有效传输，多了一次去重的动作，确保订阅者收到的消息有一次。
     * @param data 发送的文本
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic,
                    @Header(MqttHeaders.QOS) int qos,
                    String data);

}
