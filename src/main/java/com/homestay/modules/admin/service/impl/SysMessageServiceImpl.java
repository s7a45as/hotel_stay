package com.homestay.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.homestay.common.exception.BusinessException;
import com.homestay.modules.admin.entity.SysMerchant;
import com.homestay.modules.admin.entity.SysMessage;
import com.homestay.modules.admin.dto.SysMessageDTO;
import com.homestay.modules.admin.mapper.SysMerchantMapper;
import com.homestay.modules.admin.mapper.SysMessageMapper;
import com.homestay.modules.admin.service.SysMessageService;
import com.homestay.modules.merchant.entity.MerchantMessage;
import com.homestay.modules.merchant.mapper.MerchantMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage> 
    implements SysMessageService {

    private final MerchantMessageMapper adminMerchantMessageMapper;
    private final SysMerchantMapper merchantMapper;

    public SysMessageServiceImpl(MerchantMessageMapper adminMerchantMessageMapper,
                                 SysMerchantMapper merchantMapper) {
        this.adminMerchantMessageMapper = adminMerchantMessageMapper;
        this.merchantMapper = merchantMapper;
    }

    @Override
    public Page<SysMessage> getMessageList(Integer pageNum, Integer pageSize, String keyword,
                                         String type, String startDate, String endDate) {
        LambdaQueryWrapper<SysMessage> wrapper = new LambdaQueryWrapper<>();
        
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.like(SysMessage::getTitle, keyword)
                  .or()
                  .like(SysMessage::getContent, keyword);
        }
        
        if (StringUtils.isNotBlank(type)) {
            wrapper.eq(SysMessage::getType, type);
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotBlank(startDate)) {
            wrapper.ge(SysMessage::getCreateTime, LocalDateTime.parse(startDate, formatter));
        }
        if (StringUtils.isNotBlank(endDate)) {
            wrapper.le(SysMessage::getCreateTime, LocalDateTime.parse(endDate, formatter));
        }
        
        wrapper.orderByDesc(SysMessage::getCreateTime);
        
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(SysMessageDTO messageDTO) {
        SysMessage message = new SysMessage();
        BeanUtils.copyProperties(messageDTO, message);
        
        if (!save(message)) {
            throw new BusinessException("发送消息失败");
        }
        
        if ("MERCHANT".equals(messageDTO.getTargetType())) {
            saveMerchantMessage(messageDTO, message.getId());
        }
    }

    private void saveMerchantMessage(SysMessageDTO messageDTO, Long sysMessageId) {
        try {
            LambdaQueryWrapper<SysMerchant> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysMerchant::getStatus, 1);
            List<SysMerchant> merchants = merchantMapper.selectList(wrapper);
            
            List<MerchantMessage> merchantMessages = merchants.stream()
                .map(merchant -> {
                    MerchantMessage merchantMessage = new MerchantMessage();
                    merchantMessage.setMerchantId(merchant.getId());
                    merchantMessage.setTitle(messageDTO.getTitle());
                    merchantMessage.setContent(messageDTO.getContent());
                    merchantMessage.setType(messageDTO.getType());
                    merchantMessage.setPriority(messageDTO.getPriority());
                    merchantMessage.setSender("SYSTEM");
                    merchantMessage.setIsRead(false);
                    merchantMessage.setSender(sysMessageId.toString());
                    return merchantMessage;
                })
                .collect(Collectors.toList());
            
            for (MerchantMessage merchantMessage : merchantMessages) {
                adminMerchantMessageMapper.insert(merchantMessage);
            }
        } catch (Exception e) {
            log.error("保存商家消息失败", e);
            throw new BusinessException("保存商家消息失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMessage(Long id) {
        if (!removeById(id)) {
            throw new BusinessException("删除消息失败");
        }
    }
} 