package com.example.main.service;
import io.lettuce.core.dynamic.annotation.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendEmail {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;
    public boolean SendEmail(String to, String subject, String text) {
        log.info("开始准备发送邮件");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            // Send the email and return true if successful
            javaMailSender.send(message);
            log.info("sendmail say 发送完成");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

