package com.example.mqtt.service;

import com.example.mqtt.config.MqttConfigBean;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MqttConsumer implements ApplicationRunner {

    private static Logger logger= LoggerFactory.getLogger(MqttConsumer.class);

    private static MqttClient client;

    private static MqttTopic mqttTopic;

    /**
     * MQTT连接属性配置对象
     */
    @Autowired
    public MqttConfigBean mqttCofigBean;

    /**
     * 初始化参数配置
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("初始化启动MQTT连接");
        this.connect();
    }

    /**
     *  用来连接服务器
     */
    private void connect() throws Exception{
        client = new MqttClient(mqttCofigBean.getHostUrl(),mqttCofigBean.getClientId(), new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(mqttCofigBean.getUsername());
        options.setPassword(mqttCofigBean.getPassword().toCharArray());
        options.setCleanSession(false);   //是否清除session
        // 设置超时时间
        options.setConnectionTimeout(30);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            String[] msgtopic = mqttCofigBean.getMsgTopic();
            //订阅消息
            int[] qos = new int[msgtopic.length];
            for (int i = 0; i < msgtopic.length; i++) {
                qos[i]=0;
            }
            client.setCallback(new TopMsgCallback(client,options,msgtopic,qos));
            client.connect(options);
            client.subscribe(msgtopic,qos);
            logger.info("MQTT连接成功:"+mqttCofigBean.getClientId()+":"+client);
        } catch (Exception e) {
            logger.error("MQTT连接异常："+e);
        }
    }

    /**
     * 重连
     * @throws Exception
     */
    public void reConnect() throws Exception {
        if(null != client) {
            this.connect();
        }
    }

    /**
     * 订阅某个主题
     * @param topic
     * @param qos
     */
    public void subscribe(String topic,int qos){
        try {
            logger.info("topic:"+topic);
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

    public MqttTopic getMqttTopic() {
        return mqttTopic;
    }

    public void setMqttTopic(MqttTopic mqttTopic) {
        this.mqttTopic = mqttTopic;
    }
}
