package com.homestay.common.exception;

import com.homestay.common.response.ResultCode;
import lombok.Getter;

/**
 * 业务异常类
 * 用于处理业务逻辑异常，支持多种异常信息构造方式
 */
@Getter
public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 错误数据
     */
    private final transient Object data;

    /**
     * 根据错误码创建业务异常
     *
     * @param resultCode 错误码枚举
     */
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = null;
    }

    /**
     * 根据错误码和自定义消息创建业务异常
     *
     * @param resultCode 错误码枚举
     * @param message 自定义错误消息
     */
    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
        this.message = message;
        this.data = null;
    }

    /**
     * 根据错误码和错误数据创建业务异常
     *
     * @param resultCode 错误码枚举
     * @param data 错误数据
     */
    public BusinessException(ResultCode resultCode, Object data) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    /**
     * 根据错误码、自定义消息和错误数据创建业务异常
     *
     * @param resultCode 错误码枚举
     * @param message 自定义错误消息
     * @param data 错误数据
     */
    public BusinessException(ResultCode resultCode, String message, Object data) {
        super(message);
        this.code = resultCode.getCode();
        this.message = message;
        this.data = data;
    }

    /**
     * 根据自定义错误码和消息创建业务异常
     *
     * @param code 自定义错误码
     * @param message 自定义错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = null;
    }

    /**
     * 根据自定义错误码、消息和数据创建业��异常
     *
     * @param code 自定义错误码
     * @param message 自定义错误消息
     * @param data 错误数据
     */
    public BusinessException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 根据自定义消息创建业务异常
     *
     * @param message 自定义错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.ERROR.getCode();
        this.message = message;
        this.data = null;
    }

    /**
     * 根据自定义消息和数据创建业务异常
     *
     * @param message 自定义错误消息
     * @param data 错误数据
     */
    public BusinessException(String message, Object data) {
        super(message);
        this.code = ResultCode.ERROR.getCode();
        this.message = message;
        this.data = data;
    }

    /**
     * 根据原始异常和错误码创建业务异常
     *
     * @param cause 原始异常
     * @param resultCode 错误码枚举
     */
    public BusinessException(Throwable cause, ResultCode resultCode) {
        super(cause);
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = null;
    }

    /**
     * 根据原始异常、错误码和自定义消息创建业务异常
     *
     * @param cause 原始异常
     * @param resultCode 错误码枚举
     * @param message 自定义错误消息
     */
    public BusinessException(Throwable cause, ResultCode resultCode, String message) {
        super(cause);
        this.code = resultCode.getCode();
        this.message = message;
        this.data = null;
    }

    /**
     * 根据原始异常、错误码和错误数据创建业务异常
     *
     * @param cause 原始异常
     * @param resultCode 错误码枚举
     * @param data 错误数据
     */
    public BusinessException(Throwable cause, ResultCode resultCode, Object data) {
        super(cause);
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    /**
     * 获取完整的错误信息，包括原始异常信息
     */
    @Override
    public String getMessage() {
        String causeMessage = getCause() != null ? getCause().getMessage() : null;
        return String.format("错误码: %d, 错误信息: %s, 原始异常: %s", 
            code, message, causeMessage != null ? causeMessage : "无");
    }

    /**
     * 获取简短的错误信息，不包括原始异常信息
     */
    public String getShortMessage() {
        return String.format("错误码: %d, 错误信息: %s", code, message);
    }

    /**
     * 判断是否包含错误数据
     */
    public boolean hasData() {
        return data != null;
    }

    /**
     * 获取错误数据的类型
     */
    public String getDataType() {
        return data != null ? data.getClass().getSimpleName() : "null";
    }
} 