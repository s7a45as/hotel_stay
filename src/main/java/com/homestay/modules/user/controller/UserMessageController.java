package com.homestay.modules.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.homestay.common.response.Result;
import com.homestay.modules.user.dto.UserMessageDTO;
import com.homestay.modules.user.service.UserMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户-消息管理", description = "提供用户消息相关的接口")
@RestController
@RequestMapping("/user/messages")
@RequiredArgsConstructor
public class UserMessageController {

    private final UserMessageService userMessageService;

    @Operation(summary = "获取消息列表")
    @GetMapping
    public Result<IPage<UserMessageDTO>> getMessages(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "消息类型") @RequestParam(required = false) String type,
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "开始日期") @RequestParam(required = false) String startDate,
            @Parameter(description = "结束日期") @RequestParam(required = false) String endDate) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Result.success(userMessageService.getUserMessages(userId, page, pageSize, 
                type, keyword, startDate, endDate));
    }

    @Operation(summary = "标记消息为已读")
    @PutMapping("/{messageId}/read")
    public Result<Void> markMessageRead(
            @Parameter(description = "消息ID") @PathVariable Long messageId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userMessageService.markMessageRead(userId, messageId);
        return Result.success();
    }

    @Operation(summary = "批量标记消息为已读")
    @PutMapping("/batch-read")
    public Result<Void> markMessagesRead(
            @Parameter(description = "消息ID列表") @RequestBody List<Long> messageIds) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userMessageService.markMessagesRead(userId, messageIds);
        return Result.success();
    }

    @Operation(summary = "删除消息")
    @DeleteMapping("/{messageId}")
    public Result<Void> deleteMessage(
            @Parameter(description = "消息ID") @PathVariable Long messageId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userMessageService.deleteMessage(userId, messageId);
        return Result.success();
    }

    @Operation(summary = "批量删除消息")
    @DeleteMapping("/batch-delete")
    public Result<Void> deleteMessages(
            @Parameter(description = "消息ID列表") @RequestBody List<Long> messageIds) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userMessageService.deleteMessages(userId, messageIds);
        return Result.success();
    }

    @Operation(summary = "获取未读消息数量")
    @GetMapping("/unread-count")
    public Result<Integer> getUnreadCount() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Result.success(userMessageService.getUnreadCount(userId));
    }
} 