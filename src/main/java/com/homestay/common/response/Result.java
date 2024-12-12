package com.homestay.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "统一响应结果")
public class Result<T> {
    
    @Schema(description = "状态码", required = true)
    private Integer code;
    
    @Schema(description = "返回消息", required = true)
    private String message;
    
    @Schema(description = "返回数据")
    private T data;
    
    /**
     * 成功返回结果
     */
    public static <T> Result<T> success() {
        return success(null);
    }
    
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(T data) {
        return success(ResultCode.SUCCESS.getMessage(), data);
    }
    
    /**
     * 成功返回结果
     *
     * @param message 提示信息
     * @param data 获取的数据
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    
    /**
     * 失败返回结果
     */
    public static <T> Result<T> error() {
        return error(ResultCode.ERROR);
    }
    
    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Result<T> error(String message) {
        return error(ResultCode.ERROR.getCode(), message);
    }
    
    /**
     * 失败返回结果
     *
     * @param errorCode 错误码
     */
    public static <T> Result<T> error(ResultCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMessage());
    }
    
    /**
     * 失败返回结果
     *
     * @param code 状态码
     * @param message 提示信息
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 参数验证失败返回结果
     */
    public static <T> Result<T> validateFailed() {
        return error(ResultCode.VALIDATE_FAILED);
    }
    
    /**
     * 参数验证失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Result<T> validateFailed(String message) {
        return error(ResultCode.VALIDATE_FAILED.getCode(), message);
    }
    
    /**
     * 未登录返回结果
     */
    public static <T> Result<T> unauthorized() {
        return error(ResultCode.UNAUTHORIZED);
    }
    
    /**
     * 未授权返回结果
     */
    public static <T> Result<T> forbidden() {
        return error(ResultCode.FORBIDDEN);
    }
} 