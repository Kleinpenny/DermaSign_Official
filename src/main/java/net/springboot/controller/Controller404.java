package net.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Controller404 {

    @GetMapping("/404")
    public String portal(Model model) {
        return "404";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParameterException(MissingServletRequestParameterException ex) {
        // 重定向到404页面
        return new ModelAndView("redirect:404");
    }
}
