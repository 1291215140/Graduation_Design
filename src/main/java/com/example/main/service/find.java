package com.example.main.service;
import com.example.main.bean.usermessage;
import com.example.main.bool.tool;
import com.example.main.mapper.mapper;
import jakarta.servlet.http.Cookie;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Log
//创建服务类
@Service
public class find {
    @Autowired
    mapper mapper;
    public void savemessage(String username,String message)
    {
        tool bool = new tool();
        usermessage usermessage = new usermessage();
        usermessage.setUsername(username);
        usermessage.setMessage(message);
        String message1 = mapper.selectmessage(usermessage);
        if(message1==null){
            usermessage.setMessage("{" + bool.gettime() +  ":" + message+"}");
            mapper.insertmessage(usermessage);
        }
        else
        {
            //将mess字符串最后大括号去掉
            message1 = message1.substring(0,message1.length()-1);
            message1 += ","+bool.gettime()+":"+message+"}";
            usermessage.setMessage(message1);
            mapper.updatemessage(usermessage);
        }
    }

}
