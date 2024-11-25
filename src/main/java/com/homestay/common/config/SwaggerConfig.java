package com.homestay.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("民宿预订系统API文档")
                        .version("1.0")
                        .description("基于SpringBoot3 + MyBatis-Plus开发的民宿预订系统接口文档\n\n" +
                                "导出文档方式：\n" +
                                "1. 访问 /v3/api-docs 获取JSON格式文档\n" +
                                "2. 访问 /v3/api-docs.yaml 获取YAML格式文档")
                        .contact(new Contact()
                                .name("开发团队")
                                .email("457325090@qq.com")));
                
        // JWT认证配置，暂时注释掉
        /*
                .components(new Components()
                        .addSecuritySchemes("Bearer",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .name("Authorization")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer"));
        */
                

    }
} 