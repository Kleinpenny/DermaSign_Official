package net.springboot.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootTest
class SpringBootTaskApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Test
    void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("这里写测试邮件标题");
        message.setText("这里写正文内容");
        message.setTo("1315756485@qq.com"); //收件人邮箱地址,请自行修改
        message.setFrom(username);
        mailSender.send(message);
    }

}