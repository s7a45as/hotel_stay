package com.homestay.modules.house.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema(description = "房源列表项")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public  class HouseItemDTO {

    @Schema(description = "房源ID")
    private Long id;

    @Schema(description = "房源标题")
    private String title;

    @Schema(description = "封面图片")
    private String coverImage;

    @Schema(description = "价格")
    private Integer price;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "房源类型")
    private String type;

    @Schema(description = "评分")
    private Double rating;

    @Schema(description = "评价数量")
    private Integer reviewCount;

    @Schema(description = "标签列表")
    private List<TagDTO> tags;
}
