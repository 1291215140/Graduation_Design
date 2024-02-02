package com.example.main.control;

import com.example.main.mapper.messagemapper;
import com.example.main.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Slf4j
@Controller
public class MessageControl {
    @Autowired
    messagemapper messagemaper;
    @Autowired
    RedisService redisService;
    //将数据从数据库取到redis
    @ResponseBody
    @GetMapping("/loadmessage")
    boolean save(HttpServletRequest request, HttpServletResponse response)
    {
        String username = request.getParameter("username");
        log.info(username);
        if (username==null) return false;
        HashMap map = new HashMap();
        map.put("username",username);
        String message = messagemaper.selectMessage(map);
        if(message==null) return false;
        redisService.Set(username, message);
        return true;
    }
    @GetMapping("/ceshi")
    String getmessge(HttpServletRequest request, HttpServletResponse response)
    {
        return "测试环境";
    }
}
