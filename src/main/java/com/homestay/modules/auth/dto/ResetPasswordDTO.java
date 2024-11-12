package com.homestay.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "重置密码参数")
public class ResetPasswordDTO {
    
    @Schema(description = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空")
    @Pattern(
        regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",
        message = "邮箱格式不正确"
    )
    private String email;
    
    @Schema(description = "验证码", required = true)
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码必须是6位数字")
    private String code;
    
    @Schema(description = "新密码", required = true)
    @NotBlank(message = "新密码不能为空")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,20}$",
        message = "密码必须包含大小写字母和数字，长度在8-20之间"
    )
    private String newPassword;
} 