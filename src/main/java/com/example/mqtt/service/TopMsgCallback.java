package com.example.mqtt.service;

import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TopMsgCallback implements MqttCallback {

    private static Logger logger= LoggerFactory.getLogger(TopMsgCallback.class);

    private MqttClient client;
    private MqttConnectOptions options;
    private String[] topic;
    private int[] qos;

    public TopMsgCallback() {}

    public TopMsgCallback(MqttClient client, MqttConnectOptions options,String[] topic,int[] qos) {
        this.client = client;
        this.options = options;
        this.topic=topic;
        this.qos=qos;
    }

    /**
     * 断开重连
     */
    @Override
    public void connectionLost(Throwable throwable) {
        logger.info("MQTT连接断开，发起重连");
        while(true) {
            try {
                Thread.sleep(30000);
                client.connect(options);
                //订阅消息
                client.subscribe(topic,qos);
                logger.info("MQTT重新连接成功:"+client);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 消息处理
     */
    @Override
    public void messageArrived(String s, MqttMessage message) throws Exception {
        System.out.println();
        //订阅消息字符
        String msg=new String(message.getPayload());
        byte[] bymsg=getBytesFromObject(msg);
        logger.info("topic:"+topic[0]);
        logger.info("msg:"+msg);

    }

    //对象转化为字节码
    public  byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
