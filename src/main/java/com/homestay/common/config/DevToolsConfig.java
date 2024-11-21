package com.homestay.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")  // 仅在开发环境生效
public class DevToolsConfig {
    static {
        // 设置devtools的文件更新时间阈值
        System.setProperty("spring.devtools.restart.poll-interval", "2s");
        System.setProperty("spring.devtools.restart.quiet-period", "1s");
    }
}