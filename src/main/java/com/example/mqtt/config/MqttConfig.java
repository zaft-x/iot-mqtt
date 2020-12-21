package com.example.mqtt.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *EMQ连接相关配置
 *@description: 
 *@author: x.zaft
 *@Date 2020/8/13
 *@Version V1.0
 */
@Data
@Component
public class MqttConfig {

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;
    /**订阅的clientID   与发布的不能相同， 否则会提示重连异常*/
    @Value("${spring.mqtt.client.id-consumer}")
    private String clientIdConsumer;
    /**发布的clientID   与订阅的不能相同， 否则会提示重连异常*/
    @Value("${spring.mqtt.client.id-product}")
    private String clientIdProduct;
    /**主题*/
    @Value("${spring.mqtt.topic}")
    private String msgTopic;

    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout ;   //连接超时

    /**
     * 获取默认主题
     * @return
     */
    public String[] getMsgTopic() {
        String[] topic=msgTopic.split(",");
        return topic;
    }
}
