package com.example.main.service;
import com.example.main.bean.usermessage;
import com.example.main.tool.tool;
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
    public boolean savemessage(String username,String message)
    {
        tool tool = new tool();
        usermessage usermessage = new usermessage();
        usermessage.setUsername(username);
        usermessage.setMessage(message);
        String message1 = mapper.selectmessage(usermessage);
        if(message1==null){
            usermessage.setMessage("{" + tool.gettime() +  ":" + message+"}");
            try{
                mapper.insertmessage(usermessage);}
            catch (Exception e)
            {
                return false;
            }
        }
        else
        {
            //将mess字符串最后大括号去掉
            message1 = message1.substring(0,message1.length()-1);
            message1 += ","+tool.gettime()+":"+message+"}";
            usermessage.setMessage(message1);
            try{mapper.updatemessage(usermessage);}
            catch (Exception e) {return false;}
        }
        return true;
    }
    public String getname(Cookie[] cookies)
    {
        if(cookies==null) return null;

        for(int i=0;i<cookies.length;i++)
        {
            log.info("键值"+cookies[i].getName());
            if(cookies[i].getName().equals("username"))
            {
                return cookies[i].getValue();
            }
        }
        return null;
    }


}
