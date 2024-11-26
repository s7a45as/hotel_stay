package com.homestay.modules.merchant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.modules.merchant.dto.MessageStatsDTO;
import com.homestay.modules.merchant.entity.MerchantMessage;

import java.util.List;

public interface MerchantMessageService {
    
    Page<MerchantMessage> getMessageList(String type, String keyword, String startDate, 
                                       String endDate, String readStatus, Integer currentPage, Integer pageSize);
    
    MessageStatsDTO getMessageStats();
    
    void markMessageRead(List<Long> messageIds);
    
    void markAllMessagesRead();
    
    void deleteMessages(List<Long> messageIds);
    
    void clearAllMessages();
} 