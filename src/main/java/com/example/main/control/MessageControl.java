package com.example.main.control;

import com.example.main.service.find;
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
    com.example.main.service.find find;
    @ResponseBody
    @GetMapping("/savemessage")
    HashMap<String,String> save(HttpServletRequest request, HttpServletResponse response)
    {
        String name = request.getParameter("name");
        String message = request.getParameter("message");
        HashMap<String,String> map = new HashMap<>();
        log.info(name+"发来消息:"+message);
        if(name == null || message == null)
        {
            map.put("message","消息为空");
            return map;
        }
        find.savemessage(name,message);
        map.put("message","成功保存消息");
        return map;
    }
    @GetMapping("/ceshi")
    String getmessge(HttpServletRequest request, HttpServletResponse response)
    {
        return "测试环境";
    }
}
