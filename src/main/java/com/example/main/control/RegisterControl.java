package com.example.main.control;

import com.example.main.data.ResponseData;
import com.example.main.data.user;
import com.example.main.mapper.usermapper;
import com.example.main.service.SendEmail;
import com.example.main.service.Verification_Code_Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;

@Slf4j
@Controller
public class RegisterControl {
    @Autowired
    usermapper usermapper;
    @Autowired
    SendEmail sendmail;
    @Autowired
    Verification_Code_Map codemap;
    @RequestMapping("/zhuce")
    String zhuce(HttpServletRequest request, HttpServletResponse response) {
        return "register";
    }
    @ResponseBody
    @PostMapping("/register")
    ResponseData register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var username = request.getParameter("username");
        var password = request.getParameter("password");
        var code = request.getParameter("code");
        var email = request.getParameter("email");
        log.info("username:" + username + "password:" + password+"code:" + code);
        if(username == null || password == null||code == null||email == null) return new ResponseData("100");
        else
        {
            log.info("register say code="+codemap.GetValue(email));
            if(!code.equals(codemap.GetValue(email))) return new ResponseData("400");
            else
            {
                var user = new user();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                codemap.delete(email);
                if(usermapper.selectemail(user)!=null||usermapper.selectpassword(user)!=null) return new ResponseData("300");
                usermapper.insertuser(user);
                return new ResponseData("200");
            }
        }
    }

}
