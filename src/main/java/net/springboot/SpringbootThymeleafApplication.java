package net.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
public class SpringbootThymeleafApplication implements WebMvcConfigurer {
	private final LocaleChangeInterceptor localeChangeInterceptor;

	public SpringbootThymeleafApplication(LocaleChangeInterceptor localeChangeInterceptor) {
		this.localeChangeInterceptor = localeChangeInterceptor;
	}

	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
		interceptorRegistry.addInterceptor((HandlerInterceptor)this.localeChangeInterceptor);
	}

	public static void main(String[] args) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames(new String[] { "i18n/localise" });
		messageSource.setDefaultEncoding("UTF-8");
		SpringApplication.run(net.springboot.SpringbootThymeleafApplication.class, args);
	}
}
