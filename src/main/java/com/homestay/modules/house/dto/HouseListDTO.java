package com.homestay.modules.house.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "房源列表返回数据")
public class HouseListDTO {

    @Schema(description = "房源列表")
    private List<HouseItemDTO> list;

    @Schema(description = "总记录数")
    private Long total;
}

