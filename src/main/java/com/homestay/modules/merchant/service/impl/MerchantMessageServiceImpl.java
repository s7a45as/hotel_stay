package com.homestay.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.utils.SecurityUtils;
import com.homestay.modules.merchant.dto.MessageStatsDTO;
import com.homestay.modules.merchant.entity.MerchantMessage;
import com.homestay.modules.merchant.mapper.MerchantMessageMapper;
import com.homestay.modules.merchant.service.MerchantMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantMessageServiceImpl implements MerchantMessageService {

    private final MerchantMessageMapper messageMapper;

    @Override
    public Page<MerchantMessage> getMessageList(String type, String keyword, String startDate,
                                              String endDate, String readStatus, Integer currentPage, Integer pageSize) {
        Page<MerchantMessage> page = new Page<>(currentPage, pageSize);
        
        LambdaQueryWrapper<MerchantMessage> wrapper = new LambdaQueryWrapper<MerchantMessage>()
            .eq(MerchantMessage::getMerchantId, SecurityUtils.getCurrentUserId())
            .eq(StringUtils.hasText(type) && !"all".equals(type), MerchantMessage::getType, type)
            .like(StringUtils.hasText(keyword), MerchantMessage::getTitle, keyword)
            .or()
            .like(StringUtils.hasText(keyword), MerchantMessage::getContent, keyword)
            .eq(StringUtils.hasText(readStatus), MerchantMessage::getIsRead, "read".equals(readStatus))
            .orderByDesc(MerchantMessage::getCreateTime);

        if (StringUtils.hasText(startDate) && StringUtils.hasText(endDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime start = LocalDateTime.parse(startDate + " 00:00:00", 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime end = LocalDateTime.parse(endDate + " 23:59:59", 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            wrapper.between(MerchantMessage::getCreateTime, start, end);
        }

        return messageMapper.selectPage(page, wrapper);
    }

    @Override
    public MessageStatsDTO getMessageStats() {
        Long merchantId = SecurityUtils.getCurrentUserId();
        MessageStatsDTO stats = new MessageStatsDTO();
        
        // 统计总数
        stats.setTotal(messageMapper.selectCount(new LambdaQueryWrapper<MerchantMessage>()
            .eq(MerchantMessage::getMerchantId, merchantId)));
        
        // 统计未读数
        stats.setUnread(messageMapper.selectCount(new LambdaQueryWrapper<MerchantMessage>()
            .eq(MerchantMessage::getMerchantId, merchantId)
            .eq(MerchantMessage::getIsRead, false)));
        
        // 统计各类型消息数
        stats.setSystem(countByType(merchantId, "system"));
        stats.setOrder(countByType(merchantId, "order"));
        stats.setComment(countByType(merchantId, "comment"));
        stats.setSecurity(countByType(merchantId, "security"));
        
        return stats;
    }

    private Long countByType(Long merchantId, String type) {
        return messageMapper.selectCount(new LambdaQueryWrapper<MerchantMessage>()
            .eq(MerchantMessage::getMerchantId, merchantId)
            .eq(MerchantMessage::getType, type));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markMessageRead(List<Long> messageIds) {
        MerchantMessage update = new MerchantMessage();
        update.setIsRead(true);
        messageMapper.update(update, new LambdaQueryWrapper<MerchantMessage>()
            .eq(MerchantMessage::getMerchantId, SecurityUtils.getCurrentUserId())
            .in(MerchantMessage::getId, messageIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markAllMessagesRead() {
        MerchantMessage update = new MerchantMessage();
        update.setIsRead(true);
        messageMapper.update(update, new LambdaQueryWrapper<MerchantMessage>()
            .eq(MerchantMessage::getMerchantId, SecurityUtils.getCurrentUserId())
            .eq(MerchantMessage::getIsRead, false));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMessages(List<Long> messageIds) {
        messageMapper.delete(new LambdaQueryWrapper<MerchantMessage>()
            .eq(MerchantMessage::getMerchantId, SecurityUtils.getCurrentUserId())
            .in(MerchantMessage::getId, messageIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearAllMessages() {
        messageMapper.delete(new LambdaQueryWrapper<MerchantMessage>()
            .eq(MerchantMessage::getMerchantId, SecurityUtils.getCurrentUserId()));
    }
} 