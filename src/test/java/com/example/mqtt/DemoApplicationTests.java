package com.example.mqtt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }


    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
    }

}
