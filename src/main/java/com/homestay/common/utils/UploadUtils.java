package com.homestay.common.utils;

import com.homestay.common.config.UploadConfig;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class UploadUtils {

    private final UploadConfig uploadConfig;

    /**
     * 上传文件
     *
     * @param file 文件
     * @param dir 子目录
     * @return 文件访问URL
     */
    public String upload(MultipartFile file, String dir) {
        // 校验文件
        validateFile(file);

        // 生成文件名
        String fileName = generateFileName(file);

        // 生成保存路径
        String savePath = generateSavePath(dir, fileName);

        try {
            // 保存文件
            File saveFile = new File(savePath);
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
            file.transferTo(saveFile);

            // 返回访问URL
            return generateAccessUrl(dir, fileName);
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 校验文件
     */
    private void validateFile(MultipartFile file) {
        // 校验文件是否为空
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ResultCode.FILE_UPLOAD_ERROR.getCode(), "上传文件不能为空");
        }

        // 校验文件大小
        long size = file.getSize();
        if (size > uploadConfig.getMaxSize() * 1024 * 1024L) {
            throw new BusinessException(ResultCode.FILE_SIZE_EXCEED.getCode(), 
                "文件大小不能超过" + uploadConfig.getMaxSize() + "MB");
        }

        // 校验文件类型
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!StringUtils.hasText(extension) || 
            !Arrays.asList(uploadConfig.getAllowTypes()).contains(extension.toLowerCase())) {
            throw new BusinessException(ResultCode.FILE_FORMAT_ERROR.getCode(), 
                "只允许上传" + Arrays.toString(uploadConfig.getAllowTypes()) + "格式的文件");
        }
    }

    /**
     * 生成文件名
     */
    private String generateFileName(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return UUID.randomUUID().toString().replace("-", "") + "." + extension;
    }

    /**
     * 生成保存路径
     */
    private String generateSavePath(String dir, String fileName) {
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return uploadConfig.getSavePath() + "/" + dir + "/" + datePath + "/" + fileName;
    }

    /**
     * 生成访问URL
     */
    private String generateAccessUrl(String dir, String fileName) {
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return uploadConfig.getAccessUrl() + "/" + dir + "/" + datePath + "/" + fileName;
    }
} 