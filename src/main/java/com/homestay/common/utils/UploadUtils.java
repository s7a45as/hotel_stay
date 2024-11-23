package com.homestay.common.utils;

import com.homestay.common.exception.BusinessException;
import com.homestay.common.exception.FileSizeLimitExceededException;
import com.homestay.common.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
public class UploadUtils {

    @Value("${upload.save-path}")
    private String savePath;
    
    @Value("${homestay.base-url}")
    private String baseUrl;

    @Value("${spring.servlet.multipart.max-file-size:5MB}")
    private String maxFileSize;

    public String upload(MultipartFile file, String directory) {
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                throw new BusinessException(ResultCode.UPLOAD_FILE_EMPTY);
            }

            // 获取配置的最大文件大小（默认5MB）
            long maxSize = parseSize(maxFileSize);
            
            // 检查文件大小
            if (file.getSize() > maxSize) {
                throw new FileSizeLimitExceededException(
                    maxSize,
                    file.getSize()
                );
            }

            // 获取文件后缀
            String originalFilename = file.getOriginalFilename();
            String suffix = StringUtils.getFilenameExtension(originalFilename);
            if (suffix == null) {
                throw new BusinessException(ResultCode.UPLOAD_FILE_TYPE_NOT_ALLOWED);
            }
            
            // 检查文件类型
            if (!isValidImageType(suffix)) {
                throw new BusinessException(ResultCode.UPLOAD_FILE_TYPE_NOT_ALLOWED);
            }

            // 生成文件路径
            String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String fileName = UUID.randomUUID().toString().replace("-", "") + "." + suffix;
            
            // 构建目标路径
            Path uploadPath = Paths.get(savePath, directory, datePath);
            Path targetPath = uploadPath.resolve(fileName);
            
            log.info("Upload path: {}", uploadPath);
            log.info("Target path: {}", targetPath);

            // 创建目录
            Files.createDirectories(uploadPath);

            // 保存文件
            Files.copy(file.getInputStream(), targetPath);

            // 返回完整的访问URL
            String relativePath = String.format("/upload/%s/%s/%s", directory, datePath, fileName);
            String fullUrl = baseUrl + relativePath;
            log.info("File access URL: {}", fullUrl);
            
            return fullUrl;

        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException(ResultCode.UPLOAD_ERROR, "文件上传失败: " + e.getMessage());
        }
    }

    private boolean isValidImageType(String suffix) {
        if (suffix == null) return false;
        suffix = suffix.toLowerCase();
        return "jpg".equals(suffix) || 
               "jpeg".equals(suffix) || 
               "png".equals(suffix) || 
               "gif".equals(suffix);
    }

    /**
     * 解析配置的文件大小字符串，转换为字节数
     */
    private long parseSize(String size) {
        size = size.toUpperCase();
        if (size.endsWith("KB")) {
            return Long.parseLong(size.substring(0, size.length() - 2)) * 1024;
        }
        if (size.endsWith("MB")) {
            return Long.parseLong(size.substring(0, size.length() - 2)) * 1024 * 1024;
        }
        if (size.endsWith("GB")) {
            return Long.parseLong(size.substring(0, size.length() - 2)) * 1024 * 1024 * 1024;
        }
        return Long.parseLong(size);
    }
} 