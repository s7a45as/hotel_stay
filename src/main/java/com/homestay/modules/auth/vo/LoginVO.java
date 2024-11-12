package com.homestay.modules.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "登录返回信息")
public class LoginVO {
    @Schema(description = "访问令牌")
    private String token;
    @Schema(description = "用户信息")
    private UserInfo userInfo;

    @Data
    @Schema(description = "用户基本信息")
    public static class UserInfo {
        @Schema(description = "用户ID")
        private Long id;
        @Schema(description = "用户名")
        private String username;
        @Schema(description = "昵称")
        private String nickname;
        @Schema(description = "角色")
        private String role;
        @Schema(description = "头像")
        private String avatar;
        @Schema(description = "商家名称")
        private String businessName;
        @Schema(description = "商家地址")
        private String businessAddress;
    }
} 