package com.homestay.modules.house.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.homestay.common.response.Result;
import com.homestay.modules.house.entity.THouseComment;
import com.homestay.modules.house.entity.THouseCommentReport;
import com.homestay.modules.house.entity.THouseRatingStats;
import com.homestay.modules.house.service.impl.HouseCommentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "房源评论", description = "房源评论相关接口")
@RestController
@RequestMapping("/house/comment")
@RequiredArgsConstructor
@Slf4j
public class HouseCommentController {

    private final HouseCommentServiceImpl commentService;

    @Operation(summary = "获取房源评论列表")
    @GetMapping("/list")
    public Result<IPage<THouseComment>> getComments(
            @Parameter(description = "房源ID") @RequestParam Long houseId,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "评分筛选") @RequestParam(required = false) Integer rating
    ) {
        return Result.success(commentService.getCommentList(houseId, page, pageSize, rating));
    }

    @Operation(summary = "添加评论")
    @PostMapping("/{houseId}")
    public Result<Void> addComment(
            @Parameter(description = "评论信息") @RequestBody @Valid THouseComment comment,
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @Parameter(description = "房源ID") @PathVariable Long houseId
    ) {
        comment.setUser_id(userId);
        comment.setHouse_id(houseId);
        commentService.addComment(comment);
        return Result.success();
    }

    @Operation(summary = "点赞/取消点赞评论")
    @PostMapping("/{commentId}/like")
    public Result<?> toggleCommentLike(
            @Parameter(description = "评论ID") @PathVariable Long commentId,
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        boolean isLiked = commentService.toggleLike(commentId, userId);
        return Result.success(isLiked ? "点赞成功" : "取消点赞成功");
    }

    @Operation(summary = "举报评论")
    @PostMapping("/report")
    public Result<?> reportComment(
            @Parameter(description = "举报信息") @RequestBody @Valid THouseCommentReport report,
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        report.setUser_id(userId);
        commentService.reportComment(report);
        return Result.success("举报成功");
    }

    @Operation(summary = "获取评分统计")
    @GetMapping("/rating-stats/{houseId}")
    public Result<THouseRatingStats> getRatingStats(
            @Parameter(description = "房源ID") @PathVariable Long houseId
    ) {
        return Result.success(commentService.getRatingStats(houseId));
    }

    @Operation(summary = "删除评论", description = "仅评论作者可删除")
    @DeleteMapping("/{commentId}")
    public Result<?> deleteComment(
            @Parameter(description = "评论ID") @PathVariable Long commentId,
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        commentService.deleteComment(commentId, userId);
        return Result.success("删除成功");
    }

    @Operation(summary = "修改评论", description = "仅评论作者可修改")
    @PutMapping("/{commentId}")
    public Result<?> updateComment(
            @Parameter(description = "评论ID") @PathVariable Long commentId,
            @Parameter(description = "评论信息") @RequestBody @Valid THouseComment comment,
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId
    ) {
        comment.setId(commentId);
        commentService.updateComment(comment, userId);
        return Result.success("修改成功");
    }
} 