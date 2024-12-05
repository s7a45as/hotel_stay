package com.homestay.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.user.dto.UserMessageDTO;
import com.homestay.modules.user.entity.UserMessage;
import com.homestay.modules.user.mapper.UserMessageMapper;
import com.homestay.modules.user.service.UserMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserMessageServiceImpl implements UserMessageService {

    private final UserMessageMapper userMessageMapper;

    @Override
    public IPage<UserMessageDTO> getUserMessages(Long userId, Integer page, Integer pageSize,
                                               String type, String keyword,
                                               String startDate, String endDate) {
        Page<UserMessage> messagePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<UserMessage> wrapper = new LambdaQueryWrapper<UserMessage>()
                .eq(UserMessage::getUserId, userId)
                .eq(StringUtils.hasText(type), UserMessage::getType, type)
                .and(StringUtils.hasText(keyword), w -> w
                        .like(UserMessage::getTitle, keyword)
                        .or()
                        .like(UserMessage::getContent, keyword))
                .ge(StringUtils.hasText(startDate), UserMessage::getCreateTime, startDate)
                .le(StringUtils.hasText(endDate), UserMessage::getCreateTime, endDate)
                .orderByDesc(UserMessage::getCreateTime);

        IPage<UserMessage> messageIPage = userMessageMapper.selectPage(messagePage, wrapper);
        
        return messageIPage.convert(message -> {
            UserMessageDTO dto = new UserMessageDTO();
            BeanUtils.copyProperties(message, dto);
            return dto;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markMessageRead(Long userId, Long messageId) {
        int rows = userMessageMapper.markAsRead(messageId, userId);
        if (rows <= 0) {
            throw new BusinessException(ResultCode.OPERATION_FAILED, "标记消息已读失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markMessagesRead(Long userId, List<Long> messageIds) {
        int rows = userMessageMapper.batchMarkAsRead(messageIds, userId);
        if (rows <= 0) {
            throw new BusinessException(ResultCode.OPERATION_FAILED, "批量标记消息已读失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMessage(Long userId, Long messageId) {
        int rows = userMessageMapper.delete(new LambdaQueryWrapper<UserMessage>()
                .eq(UserMessage::getId, messageId)
                .eq(UserMessage::getUserId, userId));
                
        if (rows <= 0) {
            throw new BusinessException(ResultCode.OPERATION_FAILED, "删除消息失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMessages(Long userId, List<Long> messageIds) {
        int rows = userMessageMapper.delete(new LambdaQueryWrapper<UserMessage>()
                .in(UserMessage::getId, messageIds)
                .eq(UserMessage::getUserId, userId));
                
        if (rows <= 0) {
            throw new BusinessException(ResultCode.OPERATION_FAILED, "批量删除消息失败");
        }
    }

    @Override
    public Integer getUnreadCount(Long userId) {
        return Math.toIntExact(userMessageMapper.selectCount(new LambdaQueryWrapper<UserMessage>()
                .eq(UserMessage::getUserId, userId)
                .eq(UserMessage::getIsRead, false)));
    }
} 