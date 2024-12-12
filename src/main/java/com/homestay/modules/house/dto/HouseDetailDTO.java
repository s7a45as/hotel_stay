package com.homestay.modules.house.dto;

import com.homestay.common.shareentity.House;
import com.homestay.modules.house.entity.HouseFacility;
import com.homestay.modules.merchant.entity.Merchant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@Schema(description = "房源详情")
public class HouseDetailDTO {
    
    @Schema(description = "房源信息")
    private House house;
    
    @Schema(description = "设施详情列表")
    private List<HouseFacility> facilityDetails;
    
    @Schema(description = "是否已收藏")
    private boolean isFavorite;
    
    @Schema(description = "收藏数量")
    private int favoriteCount;

    @Schema(description = "商家信息")
    private MerchantInfo merchantInfo;
    @Builder
    @Schema(description = "商家信息")
    public static class MerchantInfo {
        @Schema(description = "商家ID")
        private Long id;

        @Schema(description = "商家名称")
        private String nickname;

        @Schema(description = "头像")
        private String avatar;

        @Schema(description = "手机号")
        private String phone;

        @Schema(description = "邮箱")
        private String email;

        @Schema(description = "商家地址")
        private String address;

        @Schema(description = "商家状态")
        private Integer status;

        @Schema(description = "商家简介")
        private String username;

        public static MerchantInfo fromMerchant(Merchant merchant) {
            if (merchant == null) {
                return null;
            }
            return MerchantInfo.builder()
                    .id(merchant.getId())
                    .nickname(merchant.getNickname())
                    .avatar(merchant.getAvatar())
                    .phone(merchant.getPhone())
                    .email(merchant.getEmail())
                    .address(merchant.getAddress())
                    .status(merchant.getStatus())
                    .username(merchant.getUsername())
                    .build();
        }
    }
} 