package com.example.main.Compoent;

import com.example.main.mapper.messagemapper;
import com.example.main.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
@Slf4j
@Component
public class MyScheduledTask {
    @Autowired
    messagemapper messagemapper;
    @Autowired
    RedisService redisService;
    //设置定时任务
    // 每隔一分钟执行一次
    @Scheduled(fixedRate = 600000)
    public void myTask() {
        // 这里是你的定时任务逻辑
        HashMap map = new HashMap();
        for(Object x:redisService.getAllkeys()){
            if(x.toString()==null) break;
            log.info(x.toString());
            map.put("username",x.toString());
            map.put("message",redisService.Get(x.toString()));
            redisService.Del(x.toString());
            messagemapper.insertMessage(map);
        }
    }
}
