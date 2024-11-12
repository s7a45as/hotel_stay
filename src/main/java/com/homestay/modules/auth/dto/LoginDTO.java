package com.homestay.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "登录参数")
public class LoginDTO {
    @Schema(description = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @Schema(description = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;
    
    @Schema(description = "登录类型", required = true, allowableValues = {"user", "merchant", "admin"})
    @NotBlank(message = "登录类型不能为空")
    private String type;
} 