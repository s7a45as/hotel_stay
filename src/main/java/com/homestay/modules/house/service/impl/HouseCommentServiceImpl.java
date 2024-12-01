package com.homestay.modules.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.utils.SecurityUtil;
import com.homestay.common.utils.SecurityUtils;
import com.homestay.modules.house.entity.*;
import com.homestay.modules.house.mapper.*;
import com.homestay.modules.house.service.THouseCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class HouseCommentServiceImpl extends ServiceImpl<THouseCommentMapper, THouseComment> 
    implements THouseCommentService {

    private final THouseCommentLikeMapper likeMapper;
    private final THouseCommentReportMapper reportMapper;
    private final THouseRatingStatsMapper ratingStatsMapper;
    private final SecurityUtil securityUtil; // 用于获取当前登录用户
    public IPage<THouseComment> getCommentList(Long houseId, Integer page, Integer pageSize, Integer rating) {
        LambdaQueryWrapper<THouseComment> wrapper = new LambdaQueryWrapper<THouseComment>()
                .eq(THouseComment::getHouse_id, houseId)
                .eq(THouseComment::getStatus, 1)
                .eq(rating != null, THouseComment::getRating, rating)
                .orderByDesc(THouseComment::getCreate_time);
                
        return this.page(new Page<>(page, pageSize), wrapper);
    }

    @Override
    public void addComment(THouseComment comment) {
        // 验证必填字段
        if (comment.getHouse_id() == null) {
            throw new BusinessException("房源ID不能为空");
        }
        if (comment.getRating() == null) {
            throw new BusinessException("评分不能为空");
        }
        if (StringUtils.isBlank(comment.getContent())) {
            throw new BusinessException("评论内容不能为空");
        }
        
        // 设置默认值
        comment.setUser_id(securityUtil.getCurrentUserId());
        comment.setStatus(0);  // 0-待审核
        comment.setCreate_time(new Date());
        comment.setUpdate_time(new Date());
        
        // 保存评论
        save(comment);
        
        // 更新房源评分统计
        updateRatingStats(comment.getHouse_id());
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean toggleLike(Long commentId, Long userId) {
        THouseCommentLike like = likeMapper.selectOne(
            new LambdaQueryWrapper<THouseCommentLike>()
                .eq(THouseCommentLike::getComment_id, commentId)
                .eq(THouseCommentLike::getUser_id, userId)
        );
        
        if (like == null) {
            // 添加点赞
            like = new THouseCommentLike();
            like.setComment_id(commentId);
            like.setUser_id(userId);
            likeMapper.insert(like);
            return true;
        } else {
            // 取消点赞
            likeMapper.deleteById(like.getId());
            return false;
        }
    }

    public void reportComment(THouseCommentReport report) {
        reportMapper.insert(report);
    }

    public THouseRatingStats getRatingStats(Long houseId) {
        return ratingStatsMapper.selectById(houseId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long commentId, Long userId) {
        THouseComment comment = this.getById(commentId);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        if (!comment.getUser_id().equals(userId)) {
            throw new BusinessException("无权删除他人评论");
        }
        
        this.removeById(commentId);
        updateRatingStats(comment.getHouse_id());
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateComment(THouseComment comment, Long userId) {
        THouseComment oldComment = this.getById(comment.getId());
        if (oldComment == null) {
            throw new BusinessException("评论不存在");
        }
        if (!oldComment.getUser_id().equals(userId)) {
            throw new BusinessException("无权修改他人评论");
        }
        
        this.updateById(comment);
        updateRatingStats(comment.getHouse_id());
    }

    private void updateRatingStats(Long houseId) {
        // 统计各评分数量
        Map<Integer, Integer> ratingCounts = this.list(
            new LambdaQueryWrapper<THouseComment>()
                .eq(THouseComment::getHouse_id, houseId)
                .eq(THouseComment::getStatus, 1)
        ).stream().collect(
            Collectors.groupingBy(
                THouseComment::getRating,
                Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
            )
        );

        // 计算平均分
        int totalCount = ratingCounts.values().stream().mapToInt(Integer::intValue).sum();
        double avgRating = totalCount > 0 
            ? ratingCounts.entrySet().stream()
                .mapToDouble(e -> e.getKey() * e.getValue())
                .sum() / totalCount
            : 0;

        // 更新统计数据
        THouseRatingStats stats = new THouseRatingStats();
        stats.setHouse_id(houseId);
        stats.setAverage_rating(new BigDecimal(avgRating).setScale(1, RoundingMode.HALF_UP));
        stats.setTotal_rating(totalCount);
        stats.setRating_1_count(ratingCounts.getOrDefault(1, 0));
        stats.setRating_2_count(ratingCounts.getOrDefault(2, 0));
        stats.setRating_3_count(ratingCounts.getOrDefault(3, 0));
        stats.setRating_4_count(ratingCounts.getOrDefault(4, 0));
        stats.setRating_5_count(ratingCounts.getOrDefault(5, 0));
        
        // 更新或插入
        THouseRatingStats existingStats = ratingStatsMapper.selectById(houseId);
        if (existingStats != null) {
            stats.setUpdate_time(existingStats.getUpdate_time());
            ratingStatsMapper.updateById(stats);
        } else {
            ratingStatsMapper.insert(stats);
        }
    }
} 