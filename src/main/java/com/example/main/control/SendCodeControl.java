package com.example.main.control;

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
import java.util.HashMap;
@Slf4j
@Controller
public class SendCodeControl {
    @Autowired
    usermapper usermapper;
    @Autowired
    SendEmail sendmail;
    @Autowired
    Verification_Code_Map codemap;
    @ResponseBody
    @RequestMapping("/sendCode")
    HashMap code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap map = new HashMap<>();
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        //如果传来的find是有参数的，说明是找回页面传来，如果是无参数，说明是注册页面传来的
        String find = request.getParameter("find");
        user user = new user();
        user.setEmail(email);
        user.setUsername(username);
        if (email == null) map.put("status","100");
        else
        {
            //生成验证码
            String code = "";
            for(int i = 0; i < 5; i++)
            {
                code += (int) (Math.random() * 10);
            }
            log.info("code"+code);
            if (find!=null){
                if(username!=null) map.put("status","100");//传来了意外的参数
                else
                {
                    map.put("email",email);
                    username = usermapper.selectemail(user);
                    if(username==null) map.put("status","300");
                    else {
                        if(sendmail.SendEmail(email, "验证码", "" + code))
                        {
                            map.put("status","200");
                            codemap.PutValue(email,code);
                            log.info("验证码:" + code);
                        }
                        else map.put("status","400");
                    }
                }
            }
            else
            {
                if(usermapper.selectpassword(user)!=null) map.put("status","300");
                else {
                    if (sendmail.SendEmail(email, "验证码", "" + code))
                    {
                        map.put("status", "200");
                        codemap.PutValue(email, code);
                        log.info("验证码:" + code);
                    }
                    else map.put("status", "300");
                }
            }
        }
        return map;
    }
}
