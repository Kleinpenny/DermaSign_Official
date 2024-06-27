package net.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
    @GetMapping("/news_details")
    public String portal(Model model) {
        return "news-details";
    }
    @GetMapping("/news")
    public String news(Model model) {
        return "news";
    }
}
