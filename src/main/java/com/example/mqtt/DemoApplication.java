package com.example.mqtt;

import org.slf4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    private static Logger logger= LoggerFactory.getLogger(DemoApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        logger.info("Msmq消息服务启动成功");
    }

}
