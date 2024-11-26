package net.springboot.controller;

import net.springboot.model.Product;
import net.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

@Controller
public class Redirect_Controller {

    @Autowired
    private ProductService productService;
    @GetMapping("/")
    public String portal(Model model) {
        List<Product> products = productService.getAllProducts();
        products = products.stream()
                .filter(product -> product.getPicName() != null && !product.getPicName().isEmpty())
                .filter(product -> product.getId() != 7 && product.getId() != 15) // 额外删除 id 为 7 和 8 的产品
                .collect(Collectors.toList());

        //抗衰的产品图片不需要显示
        // Shuffle the product list
        Collections.shuffle(products);
        // Select the first six products
        List<Product> randomProducts = products.subList(0, Math.min(6, products.size()));
        //选出来的六个产品，修改图片
        model.addAttribute("randomProducts", randomProducts);
        return "homepage";
    }
    @GetMapping("/about")
    public String about(Model model) {
        return "about";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        return "contact";
    }

    @GetMapping("/blog-details")
    public String news_details(Model model) {
        return "blog-details";
    }

    @GetMapping("/blogs-overview")
    public String news(Model model) {
        return "blogs-overview";
    }

    @GetMapping("/edu")
    public String edu(Model model) {
        return "education";
    }

    @GetMapping("/termOfUse")
    public String term(Model model) {
        return "term_of_use";
    }

    @GetMapping("/datenschutz")
    public String datenschutz(Model model) {
        return "datenschutz";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParameterException(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:404");
    }
}
