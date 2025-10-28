package com.vuminhha.decorstore.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ✅ Ánh xạ /uploads/** tới thư mục uploads/ ngoài project
        String uploadPath = "file:" + System.getProperty("user.dir") + "/uploads/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/");

        // Debug log
        System.out.println("=== Static Resources Configured ===");
        System.out.println("Handler: /assets/**");
        System.out.println("Location: classpath:/static/assets/");
    }
}
