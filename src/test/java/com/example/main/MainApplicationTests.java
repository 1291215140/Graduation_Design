package com.example.main;

import com.example.main.tool.tool;
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
        String message = "Hello World";
        tool tool =  new tool();
        System.out.println(tool.md5(message));
        HashMap map = new HashMap();
        map.put("cookie","sdsada");
        log.info(mapper.selectcookie(map));
    }

}
