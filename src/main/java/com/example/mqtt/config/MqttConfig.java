package com.example.mqtt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqttConfig {
    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.topic}")
    private String msgTopic;

    @Value("${spring.mqtt.completionTimeout}")
    private int completionTimeout ;   //连接超时

    /**
     * 获取用户名
     * @return
     */
    public String getUsername() {
        return this.username;
    }
    /**
     * 获取密码
     * @return
     */
    public String getPassword() {
        return this.password;
    }
    /**
     * 获取服务器连接地址
     * @return
     */
    public String getHostUrl() {
        return this.hostUrl;
    }
    /**
     * 获取客户端ID
     * @return
     */
    public String getClientId() {
        return this.clientId;
    }
    /**
     * 获取默认主题
     * @return
     */
    public String[] getMsgTopic() {
        String[] topic=msgTopic.split(",");
        return topic;
    }
    /***
     * 获取连接超时时间
     * @return
     */
    public int getCompletionTimeout() {
        return this.completionTimeout;
    }
}
