package com.example.spring.demo;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class LoggerTest {

    @Test
    public void test(){
        String name="张三";
        log.info("name:{}",name);
        log.error("error");

    }
}
