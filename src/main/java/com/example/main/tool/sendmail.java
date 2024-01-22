package com.example.main.tool;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.HashMap;
import java.util.Properties;
public class sendmail {

    public static  HashMap<String,Integer> map = new HashMap();
    //发送邮件
    public  boolean sendMail(String to,String subject,String content) throws MessagingException {
        tool tool = new tool();
        HashMap map = tool.loadfile("setting.properties");
        String host = "smtp.qq.com"; // QQ邮箱的SMTP服务器地址
        int port = 587; // SMTP服务器端口号
        final String username = (String) map.get("username"); // 发件人邮箱地址
        final String password = (String) map.get("password"); // 发件人邮箱密码或者应用程序生成的授权码（不同于登录密码）
        // 配置SMTP服务器属性
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        // 创建会话对象
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Transport transport = session.getTransport("smtp");
            transport.connect(host, port, username, password);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            // 设置邮件的接收者（收件人）
            InternetAddress[] toAddresses = {new InternetAddress(to)}; // 接收者邮箱地址
            message.addRecipients(Message.RecipientType.TO, toAddresses);
            // 设置邮件的标题
            message.setSubject(subject);
            // 设置邮件正文
            message.setText(content);
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("邮件已发送成功！");
            transport.close();
            return  true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}

