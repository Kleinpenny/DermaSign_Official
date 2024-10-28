package net.springboot.controller;

import net.springboot.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping({"/send-email"})
    public String sendEmail() {
        this.emailService.sendSimpleMessage("1315756485@qq.com", "Hello", "This is the body of the email.");
        return "/";
    }
}
