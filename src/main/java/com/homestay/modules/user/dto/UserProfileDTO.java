package com.homestay.modules.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "用户个人信息DTO")
public class UserProfileDTO {
    
    @Schema(description = "昵称")
    private String nickname;
    
    @Schema(description = "手机号")
    private String phone;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "性别(male:男 female:女 other:其他)")
    private String gender;
    
    @Schema(description = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime birthday;
    
    @Schema(description = "居住地址")
    private String address;
    
    @Schema(description = "个人简介")
    private String bio;
    
    @Schema(description = "头像URL")
    private String avatar;
} 