package com.homestay.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${upload.save-path}")
    private String uploadPath;
    /**
     * 配置资源处理器，用于处理静态资源的访问
     *
     * @param registry 资源处理器注册表，用于注册不同路径的资源处理器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置 Swagger UI 的资源处理器，处理 /swagger-ui/** 路径的请求
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");

        // 配置 Webjars 的资源处理器，处理 /webjars/** 路径的请求
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // 配置模板文件的资源处理器，处理 /templates/** 路径的请求
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");

        // 配置上传文件的资源处理器，处理 /upload/** 路径的请求
        // "file:" 表示这是一个文件系统路径，uploadPath 是上传文件的目录路径
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/audit/**").setViewName("forward:/templates/admin/merchant-audit");
    }



} 