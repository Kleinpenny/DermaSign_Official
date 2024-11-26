package net.springboot.controller;

import net.springboot.model.Product;
import net.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

@org.springframework.stereotype.Controller
public class ProductsListController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String productsPage(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "topClass", defaultValue = "all") String topClass,
            @RequestParam(value = "type", defaultValue = "all") String type,
            @RequestParam(value = "text1", defaultValue = "all") String text1,
            @RequestParam(value = "text2", defaultValue = "all") String text2,
            Model model) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        System.out.println(topClass + ' ' + type);
        int pageSize = 6;
        Page<Product> page = productService.typeFindPaginated(pageNo, pageSize, topClass, type);
        List<Product> listProducts = page.getContent();

        model.addAttribute("text1", text1);
        model.addAttribute("text2", text2);
        model.addAttribute("topClass", topClass);
        model.addAttribute("type", type);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("localeData", currentLocale.toString());
        return "products";
    }

    @GetMapping("/pro")
    public String proPage(
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
            @RequestParam(value = "topClass", defaultValue = "pro") String topClass,
            @RequestParam(value = "type", defaultValue = "pro") String type,
            Model model) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        System.out.println(topClass + ' ' + type);
        int pageSize = 6;
        Page<Product> page = productService.typeFindPaginated(pageNo, pageSize, topClass, type);
        List<Product> listProducts = page.getContent();

        model.addAttribute("topClass", topClass);
        model.addAttribute("type", type);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("localeData", currentLocale.toString());
        return "pro";
    }



//    ------------------Single Product-----------------------     //

    @GetMapping("/single-product/{id}")
    public String showFormForUpdate(
            @PathVariable ( value = "id") long id,
            Model model) {

        Locale currentLocale = LocaleContextHolder.getLocale();
        // get product from the service
        Product product = productService.getProductById(id);

        // set product as a model attribute to pre-populate the form
        model.addAttribute("product", product);
        model.addAttribute("professionalSign", product.getProfessional());

        // product Ingredient
        String[] splitArray1 = product.getIngredientsEn().split("[、,]");
        List<String> resultListEn = new ArrayList<>(Arrays.asList(splitArray1));
        // 去除字符串前后的空白并进行首字母大写处理
        resultListEn.replaceAll(ProductsListController::capitalizeEachWord);

        String[] splitArray2= product.getIngredientsDe().split("[、,]");
        List<String> resultListDe = new ArrayList<>(Arrays.asList(splitArray2));
        // 去除字符串前后的空白并进行首字母大写处理
        resultListDe.replaceAll(ProductsListController::capitalizeEachWord);

        //Usage -> #
        String[] usageArrayEn = product.getUsageEn().split("#");
        String[] usageArrayDe = product.getUsageDe().split("#");
        //Benefit -> ;
        String[] benefitArrayEn = product.getBenefitEn().replace(".", "").split(";");
        String[] benefitArrayDe = product.getBenefitDe().replace(".", "").split(";");
        //PicName
        String[] PicArray = product.getPicName().split(";");

        //warning


        model.addAttribute("ingredientEn", resultListEn);
        model.addAttribute("ingredientDe", resultListDe);
        model.addAttribute("usageArrayEn", usageArrayEn);
        model.addAttribute("usageArrayDe", usageArrayDe);
        model.addAttribute("benefitArrayEn", benefitArrayEn);
        model.addAttribute("benefitArrayDe", benefitArrayDe);
        model.addAttribute("picArray", PicArray);
        model.addAttribute("localeData", currentLocale.toString());
        model.addAttribute("id", id);
        return "single-product";
    }

    // Method to capitalize the first letter of each word while retaining the existing capitalization
    public static String capitalizeEachWord(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder result = new StringBuilder(str.length());
        String[] words = str.trim().split("\\s+");

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)));
                result.append(word.substring(1)); // append the rest of the word as is
            }
            result.append(" "); // add a space after each word
        }

        return result.toString().trim(); // Remove the trailing space
    }
}