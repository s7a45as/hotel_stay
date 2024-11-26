package com.homestay.modules.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.merchant.dto.MerchantOrderDetailDTO;
import com.homestay.modules.merchant.dto.OrderPageDTO;
import com.homestay.modules.merchant.entity.MerchantOrder;
import com.homestay.modules.merchant.enums.OrderStatus;
import com.homestay.modules.merchant.mapper.MerchantOrderMapper;
import com.homestay.modules.merchant.service.MerchantOrderService;
import com.homestay.common.utils.SecurityUtils;
import com.homestay.modules.order.dto.OrderDetailDTO;
import com.homestay.modules.order.entity.UserOrder;
import com.homestay.modules.order.enums.OrderStatusEnum;
import com.homestay.modules.order.mapper.OrderMapper;
import com.homestay.modules.order.vo.OrderDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import static com.homestay.common.utils.SecurityUtils.getCurrentUserId;

@Service
@RequiredArgsConstructor
@Slf4j
public class MerchantOrderServiceImpl implements MerchantOrderService {

    private final MerchantOrderMapper orderMapper;

    @Override
    public OrderPageDTO getOrderList(Integer currentPage, Integer pageSize, String orderId, String userName, String status) {
        Page<MerchantOrder> page = new Page<>(currentPage, pageSize);
        
        LambdaQueryWrapper<MerchantOrder> wrapper = new LambdaQueryWrapper<MerchantOrder>()
            .eq(MerchantOrder::getMerchantId, getCurrentUserId())
            .eq(StringUtils.hasText(orderId), MerchantOrder::getOrderNo, orderId)
            .like(StringUtils.hasText(userName), MerchantOrder::getUserName, userName)
            .eq(StringUtils.hasText(status), MerchantOrder::getStatus, status)
            .orderByDesc(MerchantOrder::getCreateTime);
            
        Page<MerchantOrder> result = orderMapper.selectPage(page, wrapper);
        
        return OrderPageDTO.builder()
            .total(result.getTotal())
            .currentPage(currentPage)
            .pageSize(pageSize)
            .list(result.getRecords())
            .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handleRefund(String orderId, Boolean agree) {
        MerchantOrder order = getOrderById(orderId);
        
        // 只能处理退款中的订单
        if (!OrderStatus.REFUNDING.getValue().equals(order.getStatus())) {
            throw new BusinessException(ResultCode.OPERATION_FAILED.getCode(), "只能处理退款中的订单");
        }
        
        // 更新订单状态
        order.setStatus(agree ? OrderStatus.REFUNDED.getValue() : OrderStatus.PAID.getValue());
        
        if (orderMapper.updateById(order) != 1) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }
        
        // TODO: 如果同意退款,调用支付系统进行退款操作
        if (agree) {
            // refundService.refund(order);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(String orderId, Integer status) {
        MerchantOrder order = getOrderById(orderId);
        order.setStatus(OrderStatus.getByCode(status).getValue());
        
        if (orderMapper.updateById(order) != 1) {
            throw new BusinessException(ResultCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public MerchantOrderDetailDTO getOrderDetail(String orderId) {
        // 1. 验证订单存在且属于当前用户
        MerchantOrder userOrder = getOrderById(orderId);
        log.info("orderId:{}", orderId);

        if (userOrder == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "订单不存在");
        }

        // 2. 从数据库获取订单详情（包含房源信息）
        MerchantOrderDetailDTO orderDetail = orderMapper.getOrderDetail(orderId);
        if (orderDetail == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "订单详情不存在");
        }

        // 3. 转换订单状态为可读文本
        OrderStatusEnum orderStatus = OrderStatusEnum.fromCode(Integer.valueOf(orderDetail.getStatus()));
        orderDetail.setStatus(orderStatus.name());

        // 4. 验证订单所属用户
        if (!userOrder.getMerchantId().equals(getCurrentUserId())) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "无权访问此订单");
        }

        return orderDetail;
    }



    /**
     * 获取订单并校验权限
     */
    private MerchantOrder getOrderById(String orderId) {
        MerchantOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST);
        }
        
        if (!order.getMerchantId().equals(getCurrentUserId())) {
            throw new BusinessException(ResultCode.NO_PERMISSION);
        }
        
        return order;
    }
} 