package com.homestay.common.exception;

import com.homestay.common.response.Result;
import com.homestay.common.response.ResultCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数校验异常: {}", message);
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    /**
     * 处理参数绑定异常
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("参数绑定异常: {}", message);
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    /**
     * 处理参数验证异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String message = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        log.error("参数验证异常: {}", message);
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), message);
    }

    /**
     * 处理缺少请求参数异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("缺少请求参数: {}", e.getMessage());
        return Result.error(ResultCode.VALIDATE_FAILED.getCode(), "缺少必要的请求参数: " + e.getParameterName());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统异常: ", e);
        return Result.error(ResultCode.ERROR.getCode(), "系统异常，请联系管理员");
    }

    /**
     * 添加订单相关的特定异常处理
     */
    @ExceptionHandler(OrderException.class)
    public Result<?> handleOrderException(OrderException e) {
        log.warn("订单异常: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 添加预订相关的特定异常处理
     */
    @ExceptionHandler(BookingException.class)
    public Result<?> handleBookingException(BookingException e) {
        log.warn("预订异常: {}", e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理文件上传大小超限异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("文件上传大小超出限制", e);
        String message = "文件大小不能超过" + 
            (e.getMaxUploadSize() / (1024 * 1024)) + "MB";
        return Result.error(ResultCode.UPLOAD_FILE_SIZE_EXCEED.getCode(), message);
    }

    /**
     * 处理单个文件大小超限异常
     */
    @ExceptionHandler(FileSizeLimitExceededException.class)
    public Result<Void> handleFileSizeLimitExceededException(FileSizeLimitExceededException e) {
        log.error("单个文件大小超出限制: 允许{}MB, 实际{}MB", 
                e.getPermittedSize() / (1024 * 1024), 
                e.getActualSize() / (1024 * 1024));
        
        String message = String.format("单个文件大小不能超过%dMB", 
                e.getPermittedSize() / (1024 * 1024));
                
        return Result.error(ResultCode.UPLOAD_FILE_SIZE_EXCEED.getCode(), message);
    }

    /**
     * 处理文件上传相关的其他异常
     */
    @ExceptionHandler(MultipartException.class)
    public Result<Void> handleMultipartException(MultipartException e) {
        log.error("文件上传失败", e);
        return Result.error(ResultCode.UPLOAD_ERROR.getCode(), "文件上传失败，请检查文件大小或格式");
    }
} 