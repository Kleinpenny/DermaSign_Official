package net.springboot.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HomepageController {
    @GetMapping("/")
    public String portal(Model model) {
        return "homepage";
    }
}
