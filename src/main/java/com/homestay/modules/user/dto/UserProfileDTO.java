package com.homestay.modules.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "用户个人信息DTO")
public class UserProfileDTO {
    
    @Schema(description = "用户ID")
    private Long id;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "昵称")
    private String nickname;
    
    @Schema(description = "头像")
    private String avatar;
    
    @Schema(description = "手机号")
    private String phone;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "性别")
    private String gender;
    
    @Schema(description = "生日")
    private LocalDateTime birthday;
    
    @Schema(description = "地址")
    private String address;
    
    @Schema(description = "个人简介")
    private String bio;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
} 