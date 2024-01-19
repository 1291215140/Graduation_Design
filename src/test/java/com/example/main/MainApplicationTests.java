package com.example.main;

import com.example.main.bean.usermessage;
import com.example.main.mapper.mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainApplicationTests {
    @Autowired
    mapper mapper;
    @Test
    void contextLoads() {
        String message = "Hello World";
        usermessage mes = new usermessage();
        mes.setUsername("t");
        System.out.println(mapper.selectmessage(mes));
    }

}
