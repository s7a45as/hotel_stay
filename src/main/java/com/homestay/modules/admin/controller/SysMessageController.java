package com.homestay.modules.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.response.Result;
import com.homestay.modules.admin.dto.SysMessageDTO;
import com.homestay.modules.admin.entity.SysMessage;
import com.homestay.modules.admin.service.SysMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "系统消息管理")
@RestController
@RequestMapping("/admin/messages")
@RequiredArgsConstructor
public class SysMessageController {

    private final SysMessageService messageService;

    @Operation(summary = "获取消息列表")
    @GetMapping
    public Result<Page<SysMessage>> getMessageList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return Result.success(messageService.getMessageList(page, pageSize, keyword, 
                                                         type, startDate, endDate));
    }

    @Operation(summary = "发送系统消息")
    @PostMapping("/send")
    public Result<Void> sendMessage(@Valid @RequestBody SysMessageDTO messageDTO) {
        messageService.sendMessage(messageDTO);
        return Result.success();
    }

    @Operation(summary = "删除消息")
    @DeleteMapping("/{id}")
    public Result<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return Result.success();
    }
} 