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
import com.homestay.modules.user.entity.UserInfo;
import com.homestay.modules.user.entity.UserMessage;
import com.homestay.modules.user.mapper.UserMapper;
import com.homestay.modules.user.mapper.UserMessageMapper;
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

    private final MerchantMessageMapper merchantMessageMapper;
    private final SysMerchantMapper merchantMapper;
    private final UserMessageMapper userMessageMapper;
    private final UserMapper userMapper;

    public SysMessageServiceImpl(MerchantMessageMapper merchantMessageMapper,
                               SysMerchantMapper merchantMapper,
                               UserMessageMapper userMessageMapper,
                               UserMapper userMapper) {
        this.merchantMessageMapper = merchantMessageMapper;
        this.merchantMapper = merchantMapper;
        this.userMessageMapper = userMessageMapper;
        this.userMapper = userMapper;
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
        // 保存系统消息
        SysMessage message = new SysMessage();
        BeanUtils.copyProperties(messageDTO, message);
        
        if (!save(message)) {
            throw new BusinessException("发送消息失败");
        }
        
        // 根据发送对象类型处理
        String targetType = messageDTO.getTargetType();
        if ("MERCHANT".equals(targetType)) {
            // 发送给商家
            saveMerchantMessage(messageDTO, message.getId());
        } else if ("USER".equals(targetType) || "ALL".equals(targetType)) {
            // 发送给普通用户或所有用户
            saveUserMessage(messageDTO, message.getId());
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
                    return merchantMessage;
                })
                .collect(Collectors.toList());
            
            for (MerchantMessage merchantMessage : merchantMessages) {
                merchantMessageMapper.insert(merchantMessage);
            }
        } catch (Exception e) {
            log.error("保存商家消息失败", e);
            throw new BusinessException("保存商家消息失败");
        }
    }

    /**
     * 保存用户消息
     */
    private void saveUserMessage(SysMessageDTO messageDTO, Long sysMessageId) {
        try {
            // 查询所有有效用户
            LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserInfo::getStatus, 1)  // 状态正常的用户
                  .eq(UserInfo::getDeleted, 0); // 未删除的用户
            
            List<UserInfo> users = userMapper.selectList(wrapper);
            
            // 批量构建用户消息
            List<UserMessage> userMessages = users.stream()
                .map(user -> {
                    UserMessage userMessage = new UserMessage();
                    userMessage.setUserId(user.getId());
                    userMessage.setTitle(messageDTO.getTitle());
                    userMessage.setContent(messageDTO.getContent());
                    userMessage.setType(messageDTO.getType());
                    userMessage.setTargetType(messageDTO.getTargetType());
                    userMessage.setPriority(messageDTO.getPriority());
                    userMessage.setSender("SYSTEM");
                    userMessage.setIsRead(false);
                    userMessage.setSysMessageId(sysMessageId);
                    return userMessage;
                })
                .collect(Collectors.toList());
            
            // 批量保存用户消息
            for (UserMessage userMessage : userMessages) {
                userMessageMapper.insert(userMessage);
            }
            
            log.info("成功发送消息给{}个用户", userMessages.size());
            
        } catch (Exception e) {
            log.error("保存用户消息失败", e);
            throw new BusinessException("保存用户消息失败");
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