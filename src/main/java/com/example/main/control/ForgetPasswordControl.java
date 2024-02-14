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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
@Slf4j
@Controller
public class ForgetPasswordControl {
    @Autowired
    usermapper usermapper;
    @Autowired
    SendEmail sendmail;
    @Autowired
    Verification_Code_Map codemap;
    @RequestMapping("/forgetpassword")
    String forgetpassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "forgetpassword";
    }
    @ResponseBody
    @RequestMapping("/setpassword")
    ResponseData findpassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var code = request.getParameter("code");
        var email = request.getParameter("email");
        var password = request.getParameter("password");
        log.info("findpassowrd say  code:"+code+"email:"+email+"password:"+password);
        if (code == null || email == null || password == null) return new ResponseData("100");
        else
        {
            if(!codemap.GetValue(email).equals(code)) return new ResponseData("300");
            else
            {
                codemap.delete(email);
                var user = new user();
                user.setEmail(email);
                user.setPassword(password);
                if(usermapper.updatepassword(user)) return new ResponseData("200");
                else {
                    return new ResponseData("400");
                }
            }
        }
    }
}
