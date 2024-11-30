package com.homestay.modules.house.dto;

import com.homestay.common.shareentity.House;
import com.homestay.modules.house.entity.HouseFacility;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
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
} 