package com.homestay.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {

    /**
     * 上传文件保存路径
     */
    private String savePath;

    /**
     * 上传文件访问URL前缀
     */
    private String accessUrl;

    /**
     * 允许上传的文件类型
     */
    private String[] allowTypes;

    /**
     * 最大文件大小(MB)
     */
    private Integer maxSize;
} 