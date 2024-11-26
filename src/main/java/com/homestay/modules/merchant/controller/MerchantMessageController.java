package com.homestay.modules.merchant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.response.Result;
import com.homestay.modules.merchant.dto.MessageStatsDTO;
import com.homestay.modules.merchant.entity.MerchantMessage;
import com.homestay.modules.merchant.service.MerchantMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商家后台-消息中心", description = "提供商家消息管理功能")
@RestController
@RequestMapping("/merchant/messages")
@RequiredArgsConstructor
public class MerchantMessageController {

    private final MerchantMessageService messageService;

    @Operation(summary = "获取消息列表")
    @GetMapping
    public Result<Page<MerchantMessage>> getMessageList(
            @Parameter(description = "消息类型") @RequestParam(required = false) String type,
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "开始日期") @RequestParam(required = false) String startDate,
            @Parameter(description = "结束日期") @RequestParam(required = false) String endDate,
            @Parameter(description = "阅读状态") @RequestParam(required = false) String readStatus,
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer currentPage,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        return Result.success(messageService.getMessageList(type, keyword, startDate, endDate, readStatus, currentPage, pageSize));
    }

    @Operation(summary = "获取消息统计数据")
    @GetMapping("/stats")
    public Result<MessageStatsDTO> getMessageStats() {
        return Result.success(messageService.getMessageStats());
    }

    @Operation(summary = "标记消息为已读")
    @PostMapping("/read")
    public Result<Void> markMessageRead(@RequestBody List<Long> messageIds) {
        messageService.markMessageRead(messageIds);
        return Result.success();
    }

    @Operation(summary = "标记所有消息为已读")
    @PostMapping("/read-all")
    public Result<Void> markAllMessagesRead() {
        messageService.markAllMessagesRead();
        return Result.success();
    }

    @Operation(summary = "删除消息")
    @DeleteMapping
    public Result<Void> deleteMessages(@RequestBody List<Long> messageIds) {
        messageService.deleteMessages(messageIds);
        return Result.success();
    }

    @Operation(summary = "清空所有消息")
    @DeleteMapping("/clear")
    public Result<Void> clearAllMessages() {
        messageService.clearAllMessages();
        return Result.success();
    }
} 