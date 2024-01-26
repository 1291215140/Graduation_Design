package com.example.main;

import com.example.main.mapper.mapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
@Slf4j
@SpringBootTest
class MainApplicationTests {
    @Autowired
    mapper mapper;
    @Test
    void contextLoads() {

    }

}
