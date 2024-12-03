package com.homestay.modules.merchant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "消息ID请求对象")
public class MessageIdsRequest {
    @Schema(description = "消息ID列表")
    private List<Long> messageIds;
}
