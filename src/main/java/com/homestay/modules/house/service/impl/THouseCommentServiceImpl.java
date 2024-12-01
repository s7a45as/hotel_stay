package com.homestay.modules.house.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.utils.SecurityUtil;
import com.homestay.modules.house.entity.*;
import com.homestay.modules.house.mapper.*;
import com.homestay.modules.house.service.THouseCommentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class THouseCommentServiceImpl extends ServiceImpl<THouseCommentMapper, THouseComment>
    implements THouseCommentService {

    private final THouseCommentLikeMapper likeMapper;
    private final THouseCommentReportMapper reportMapper;
    private final THouseRatingStatsMapper ratingStatsMapper;
    private final SecurityUtil securityUtil;

    @Override
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
        if (comment.getHouse_id() == null) {
            throw new BusinessException("房源ID不能为空");
        }
        if (comment.getRating() == null) {
            throw new BusinessException("评分不能为空");
        }
        if (StringUtils.isBlank(comment.getContent())) {
            throw new BusinessException("评论内容不能为空");
        }
        if (comment.getOrder_id() == null) {
            throw new BusinessException("订单ID不能为空");
        }
        
        comment.setUser_id(securityUtil.getCurrentUserId());
        comment.setStatus(0);
        comment.setCreate_time(new Date());
        comment.setUpdate_time(new Date());
        
        save(comment);
        updateRatingStats(comment.getHouse_id());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleLike(Long commentId, Long userId) {
        THouseCommentLike like = likeMapper.selectOne(
            new LambdaQueryWrapper<THouseCommentLike>()
                .eq(THouseCommentLike::getComment_id, commentId)
                .eq(THouseCommentLike::getUser_id, userId)
        );
        
        if (like == null) {
            like = new THouseCommentLike();
            like.setComment_id(commentId);
            like.setUser_id(userId);
            likeMapper.insert(like);
            return true;
        } else {
            likeMapper.deleteById(like.getId());
            return false;
        }
    }

    @Override
    public void reportComment(THouseCommentReport report) {
        reportMapper.insert(report);
    }

    @Override
    public THouseRatingStats getRatingStats(Long houseId) {
        return ratingStatsMapper.selectById(houseId);
    }

    @Override
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

    @Override
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
        // TODO: 实现评分统计更新逻辑
    }
}




