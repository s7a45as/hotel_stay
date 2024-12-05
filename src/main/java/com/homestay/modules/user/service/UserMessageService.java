package com.homestay.modules.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.homestay.modules.user.dto.UserMessageDTO;
import java.util.List;

public interface UserMessageService {
    
    /**
     * 获取用户消息列表
     */
    IPage<UserMessageDTO> getUserMessages(Long userId, Integer page, Integer pageSize, 
                                        String type, String keyword, 
                                        String startDate, String endDate);
    
    /**
     * 标记消息为已读
     */
    void markMessageRead(Long userId, Long messageId);
    
    /**
     * 批量标记消息为已读
     */
    void markMessagesRead(Long userId, List<Long> messageIds);
    
    /**
     * 删除消息
     */
    void deleteMessage(Long userId, Long messageId);
    
    /**
     * 批量删除消息
     */
    void deleteMessages(Long userId, List<Long> messageIds);
    
    /**
     * 获取未读消息数量
     */
    Integer getUnreadCount(Long userId);
} 