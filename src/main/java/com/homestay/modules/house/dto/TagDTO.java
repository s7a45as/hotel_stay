package com.homestay.modules.house.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "标签信息")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDTO {

    @Schema(description = "标签类型")
    private String type;

    @Schema(description = "标签文本")
    private String text;
}
