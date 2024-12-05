package com.homestay.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.homestay.modules.admin.entity.SysMessage;
import com.homestay.modules.admin.dto.SysMessageDTO;

public interface SysMessageService extends IService<SysMessage> {
    
    Page<SysMessage> getMessageList(Integer pageNum, Integer pageSize, String keyword, 
                                  String type, String startDate, String endDate);
    
    void sendMessage(SysMessageDTO messageDTO);
    
    void deleteMessage(Long id);
} 