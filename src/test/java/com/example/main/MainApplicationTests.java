package com.example.main;
import com.example.main.service.RedisService;
import com.example.main.service.SendEmail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
class MainApplicationTests {
    @Autowired
    RedisService redisService;
    @Autowired
    SendEmail sendEmail;
    @Test
    void contextLoads()  {
        log.info("hello");
    }


}
