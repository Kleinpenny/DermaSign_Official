package net.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TestController {
    @GetMapping("/three-test")
    public String test3(Model model) {
        return "/others/THREE-test";
    }

    @GetMapping("/test")
    public String test(Model model) {
        return "/others/test";
    }

    @GetMapping("/test-shop")
    public String test_shop(Model model) {
        return "/others/shopcategorie";
    }
}
