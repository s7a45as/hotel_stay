package com.homestay.modules.house.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.modules.house.entity.THouseComment;
import com.homestay.modules.house.entity.THouseCommentReport;
import com.homestay.modules.house.entity.THouseRatingStats;

/**
* @author 45732
* @description 针对表【t_house_comment(房源评论表)】的数据库操作Service
* @createDate 2024-12-01 14:27:35
*/
public interface THouseCommentService extends IService<THouseComment> {
    IPage<THouseComment> getCommentList(Long houseId, Integer page, Integer pageSize, Integer rating);
    void addComment(THouseComment comment);
    boolean toggleLike(Long commentId, Long userId);
    void reportComment(THouseCommentReport report);
    THouseRatingStats getRatingStats(Long houseId);
    void deleteComment(Long commentId, Long userId);
    void updateComment(THouseComment comment, Long userId);
}
