package com.homestay.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "商家注册参数")
public class MerchantRegisterDTO {
    
    @Schema(description = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{4,20}$", message = "用户名只能包含字母、数字、下划线和连字符，长度4-20位")
    private String username;
    
    @Schema(description = "密码", required = true)
    @NotBlank(message = "密码不能为空")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,20}$",
//            message = "密码必须包含大小写字母和数字，长度在6-20之间")
    private String password;
    
    @Schema(description = "昵称", required = true)
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    
    @Schema(description = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Schema(description = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    @Schema(description = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码必须是6位数字")
    private String verifyCode;
    
    @Schema(description = "商家名称", required = true)
    @NotBlank(message = "商家名称不能为空")
    private String businessName;
    
    @Schema(description = "营业执照号", required = true)
    @NotBlank(message = "营业执照号不能为空")
    @Pattern(regexp = "^[A-Za-z0-9]{15,18}$", message = "请输入正确的营业执照号")
    private String businessLicense;
    
    @Schema(description = "商家地址", required = true)
    @NotBlank(message = "商家地址不能为空")
    private String businessAddress;
    
    @Schema(description = "联系人", required = true)
    @NotBlank(message = "联系人不能为空")
    private String contactPerson;
} 