package com.homestay.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API 统一返回状态码
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    
    // 通用状态码 1xx
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限"),
    NOT_FOUND(404, "请求的资源不存在"),
    
    // 用户相关错误 2xxx
    USER_NOT_EXIST(2001, "用户不存在"),
    USERNAME_OR_PASSWORD_ERROR(2002, "用户名或密码错误"),
    ACCOUNT_DISABLED(2003, "账号已被禁用"),
    USERNAME_EXIST(2004, "用户名已存在"),
    PHONE_EXIST(2005, "手机号已被注册"),
    EMAIL_EXIST(2006, "邮箱已被注册"),
    EMAIL_NOT_EXIST(2007, "邮箱未注册"),
    OLD_PASSWORD_ERROR(2008, "原密码错误"),
    TWO_PASSWORDS_NOT_MATCH(2009, "两次输入的密码不一致"),
    INVALID_LOGIN_TYPE(2010, "无效的登录类型"),
    ACCOUNT_ERROR(2011, "账号状态异常"),
    ACCOUNT_PENDING(2012, "账号待审核"),
    
    // 商家相关错误 3xxx
    MERCHANT_NOT_EXIST(3001, "商家不存在"),
    MERCHANT_DISABLED(3002, "商家账号已被禁用"),
    MERCHANT_PENDING(3003, "商家账号待审核"),
    MERCHANT_STATUS_ERROR(3004, "商家状态异常"),
    BUSINESS_LICENSE_EXIST(3005, "营业执照号已存在"),
    INVALID_MERCHANT_INFO(3006, "商家信息不完整或无效"),
    MERCHANT_AUDIT_FAILED(3007, "商家审核未通过"),
    
    // 验证码相关错误 4xxx
    VERIFY_CODE_ERROR(4001, "验证码错误"),
    VERIFY_CODE_EXPIRED(4002, "验证码已过期"),
    VERIFY_CODE_FREQUENT(4003, "验证码发送太频繁，请稍后再试"),
    VERIFY_CODE_SEND_FAILED(4004, "验证码发送失败"),
    
    // 文件相关错误 5xxx
    FILE_UPLOAD_ERROR(5001, "文件上传失败"),
    FILE_FORMAT_ERROR(5002, "文件格式不正确"),
    FILE_SIZE_EXCEED(5003, "文件大小超出限制"),
    FILE_NOT_FOUND(5004, "文件不存在"),
    
    // 业务相关错误 6xxx
    OPERATION_FAILED(6001, "操作失败"),
    DATA_NOT_EXIST(6002, "数据不存在"),
    DATA_ALREADY_EXIST(6003, "数据已存在"),
    DATA_ADD_FAILED(6004, "数据添加失败"),
    DATA_UPDATE_FAILED(6005, "数据更新失败"),
    DATA_DELETE_FAILED(6006, "数据删除失败"),
    
    // 系统错误 9xxx
    SYSTEM_ERROR(9001, "系统错误"),
    NETWORK_ERROR(9002, "网络错误"),
    SERVICE_UNAVAILABLE(9003, "服务不可用"),
    GATEWAY_ERROR(9004, "网关错误"),
    THIRD_PARTY_SERVICE_ERROR(9005, "第三方服务错误"),
    DATABASE_ERROR(9006, "数据库操作错误"),
    CACHE_ERROR(9007, "缓存操作错误"),
    
    // 权限相关错误 8xxx
    NO_PERMISSION(8001, "没有操作权限"),
    TOKEN_EXPIRED(8002, "Token已过期"),
    TOKEN_INVALID(8003, "Token无效"),
    TOKEN_SIGNATURE_ERROR(8004, "Token签名错误"),
    ROLE_NOT_EXIST(8005, "角色不存在"),
    PERMISSION_DENIED(8006, "权限不足"),
    
    // 参数相关错误 1000-1999
    PARAM_ERROR(1000, "参数错误"),
    PARAM_IS_NULL(1001, "参数为空"),
    PARAM_TYPE_ERROR(1002, "参数类型错误"),
    PARAM_NOT_COMPLETE(1003, "参数缺失"),
    
    // 用户相关错误 2000-2999
    USER_PASSWORD_ERROR(2001, "密码错误"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_ACCOUNT_LOCKED(2003, "账号已被锁定"),
    USER_ACCOUNT_NOT_EXIST(2004, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2005, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2006, "账号下线"),
    
    // 业务相关错误 3000-3999
    UPDATE_ERROR(3000, "更新失败"),
    DELETE_ERROR(3001, "删除失败"),
    ADD_ERROR(3002, "添加失败"),
    QUERY_ERROR(3003, "查询失败");
    
    private final Integer code;
    private final String message;

}