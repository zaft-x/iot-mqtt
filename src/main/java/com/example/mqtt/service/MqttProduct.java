package com.example.mqtt.service;

import com.example.mqtt.config.MqttConfig;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 *生产 发布
 *@description:
 *@author: x.zaft
 *@Date 2020/8/15
 *@Version V1.0
 */
@Configuration
public class MqttProduct {
    private static Logger logger= LoggerFactory.getLogger(MqttProduct.class);
    /**
     * MQTT连接属性配置对象
     */
    @Autowired
    public MqttConfig mqttConfig;
    // 客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息
    private static final byte[] WILL_DATA;
    static {
        WILL_DATA = "offline".getBytes();
    }

    public static final String CHANNEL_NAME_OUT = "mqttOutboundChannel";
    /**
     * MQTT连接器选项
     */
    @Bean
    public MqttConnectOptions getSenderMqttConnectOptions(){
        MqttConnectOptions options=new MqttConnectOptions();
        // 设置连接的用户名
        if(!mqttConfig.getUsername().trim().equals("")){
            options.setUserName(mqttConfig.getUsername());
        }
        // 设置连接的密码
        options.setPassword(mqttConfig.getPassword().toCharArray());
        // 设置连接的地址
        options.setServerURIs(mqttConfig.getHostUrl().split(","));
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
        // 但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        // 设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
        options.setWill("willTopic", WILL_DATA, 2, false);
        return options;
    }
    /**
     * MQTT客户端
     */
    @Bean
    public MqttPahoClientFactory senderMqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getSenderMqttConnectOptions());
        return factory;
    }

    /**
     * MQTT信息通道（生产者）
     */
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
    /**
     * MQTT消息处理器（生产者）
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(
                mqttConfig.getClientIdProduct(),
                senderMqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(mqttConfig.getMsgTopic()[0]);
        return messageHandler;
    }
}
