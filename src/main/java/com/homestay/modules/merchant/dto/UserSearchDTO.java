package com.homestay.modules.merchant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户搜索结果DTO")
public class UserSearchDTO {
    
    @Schema(description = "用户ID")
    private Long id;
    
    @Schema(description = "用户手机号")
    private String phone;
    
    @Schema(description = "用户姓名")
    private String name;
} 