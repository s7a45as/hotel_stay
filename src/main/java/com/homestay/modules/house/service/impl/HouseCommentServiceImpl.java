package com.homestay.modules.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.utils.SecurityUtil;
import com.homestay.common.utils.SecurityUtils;
import com.homestay.common.utils.UploadUtils;
import com.homestay.modules.house.entity.*;
import com.homestay.modules.house.mapper.*;
import com.homestay.modules.house.service.THouseCommentService;
import com.homestay.modules.order.entity.UserOrder;
import com.homestay.modules.order.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private final OrderMapper orderMapper;
    private final UploadUtils uploadUtils;
    public IPage<THouseComment> getCommentList(Long houseId, Integer page, Integer pageSize, Integer rating) {
        LambdaQueryWrapper<THouseComment> wrapper = new LambdaQueryWrapper<THouseComment>()
                .eq(THouseComment::getHouse_id, houseId)
                .eq(THouseComment::getStatus, 1)
                .eq(rating != null, THouseComment::getRating, rating)
                .orderByDesc(THouseComment::getCreate_time);
                
        return this.page(new Page<>(page, pageSize), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(THouseComment comment) {
        // 1. 基础参数验证
        if (comment == null) {
            throw new BusinessException("评论信息不能为空");
        }
        if (comment.getHouse_id() == null) {
            throw new BusinessException("房源ID不能为空");
        }
        if (comment.getRating() == null || comment.getRating() < 1 || comment.getRating() > 5) {
            throw new BusinessException("评分必须在1-5之间");
        }
        if (StringUtils.isBlank(comment.getContent())) {
            throw new BusinessException("评论内容不能为空");
        }
        if (comment.getContent().length() > 1000) {
            throw new BusinessException("评论内容不能超过1000字");
        }

        // 2. 获取当前用户ID
        Long userId = securityUtil.getCurrentUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }
        Long houseId = Long.valueOf(comment.getHouse_id());

        // 3. 检查是否已经评论过
        Long existingComment = baseMapper.selectCount(
                new LambdaQueryWrapper<THouseComment>()
                        .eq(THouseComment::getUser_id, userId)
                        .eq(THouseComment::getHouse_id, houseId)
        );
        if (existingComment > 0) {
            throw new BusinessException("您已经评论过该房源");
        }

        // 4. 查询并验证订单
        UserOrder order = orderMapper.selectOne(
                new LambdaQueryWrapper<UserOrder>()
                        .eq(UserOrder::getUserId, userId)
                        .eq(UserOrder::getHouseId, houseId)
                        .eq(UserOrder::getStatus, 3)
                        .orderByDesc(UserOrder::getCreateTime)
                        .last("LIMIT 1")
        );
        if (order == null) {
            throw new BusinessException("您没有该房源的已完成订单，无法评价");
        }

// 5. 处理图片列表
List<String> imageUrls = new ArrayList<>();
if (comment.getImages() != null && !comment.getImages().isEmpty()) {
    if (comment.getImages().size() > 9) {
        throw new BusinessException("最多只能上传9张图片");
    }
    
    for (Object img : comment.getImages()) {
        if (img instanceof MultipartFile) {
            // 使用UploadUtils上传图片
            String url = uploadUtils.upload((MultipartFile) img, "comment");
            imageUrls.add(url);
        } else {
            throw new BusinessException("无效的图片格式，请上传正确的图片文件");
        }
    }
}
comment.setImages(imageUrls);



        // 6. 设置评论基本信息
        comment.setOrder_id(order.getId());
        comment.setUser_id(String.valueOf(userId));
        comment.setStatus(0); // 待审核状态
        Date now = new Date();
        comment.setCreate_time(now);
        comment.setUpdate_time(now);

        try {
            // 7. 保存评论
            save(comment);
            // 8. 更新房源评分统计
            updateRatingStats(Long.valueOf(comment.getHouse_id()));
        } catch (Exception e) {
            log.error("保存评论失败", e);
            throw new BusinessException("评论保存失败，请稍后重试");
        }
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
        THouseRatingStats stats = ratingStatsMapper.selectById(houseId);
        log.debug("查询到的评分统计: {}", stats); // 添加调试日志
        
        if (stats == null) {
            log.info("创建评分统计对象");
            stats = new THouseRatingStats();
            stats.setHouse_id(houseId);
            stats.setTotal_rating(0);
            stats.setAverage_rating(BigDecimal.ZERO);
            stats.setRating_1_count(0);
            stats.setRating_2_count(0);
            stats.setRating_3_count(0);
            stats.setRating_4_count(0);
            stats.setRating_5_count(0);
            stats.setUpdate_time(new Date());
            ratingStatsMapper.insert(stats);
        }
        return stats;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long commentId) {
        THouseComment comment = this.getById(commentId);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        log.debug("删除的评论ID: {}", commentId);
        log.debug("删除的用户ID1: {}", comment.getUser_id());
        log.debug("删除的用户ID2: {}", securityUtil.getCurrentUserId());
        //comment.getUser_id().equals(String.valueOf(securityUtil.getCurrentUserId()) 类型和数据需要都一样
        if (!comment.getUser_id().equals(String.valueOf(securityUtil.getCurrentUserId()))) {
            throw new BusinessException("无权删除他人评论");
        }
        
        this.removeById(commentId);
        updateRatingStats(Long.valueOf(comment.getHouse_id()));
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
        updateRatingStats(Long.valueOf(comment.getHouse_id()));
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