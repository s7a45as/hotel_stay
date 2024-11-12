package com.homestay.modules.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.constant.BookingConstants;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.common.shareentity.House;
import com.homestay.modules.house.service.HouseService;
import com.homestay.modules.order.dto.OrderCreateDTO;
import com.homestay.modules.order.dto.OrderDetailDTO;
import com.homestay.modules.order.entity.Order;
import com.homestay.modules.order.mapper.OrderMapper;
import com.homestay.modules.order.service.UserOrderService;
import com.homestay.modules.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.homestay.common.utils.SecurityUtil;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserOrderServiceImpl implements UserOrderService {

    private final OrderMapper orderMapper;
    private final HouseService houseService;
    private final PaymentService paymentService;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object createOrder(OrderCreateDTO createDTO) {
        // 验证房源是否存在

        House house = houseService.getById(createDTO.getHouseId());
        if (house == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "房源不存在");
        }
        
        // 验证日期
        long days = ChronoUnit.DAYS.between(createDTO.getCheckIn(), createDTO.getCheckOut());
        if (days <= 0) {
            throw new BusinessException("退房日期必须大于入住日期");
        }
        if (days > BookingConstants.Rules.MAX_BOOKING_DAYS) {
            throw new BusinessException("最大预订天数不能超过" + BookingConstants.Rules.MAX_BOOKING_DAYS + "天");
        }
        
        // 创建订单
        Order order = new Order();
        BeanUtils.copyProperties(createDTO, order);
        order.setUserId(getCurrentUserId());
        order.setNights((int) days);
        order.setAmount(house.getPrice().multiply(new BigDecimal(days)));
        order.setStatus(BookingConstants.Status.PENDING_PAYMENT);
        
        orderMapper.insert(order);
        
        return new HashMap<String, Object>() {{
            put("orderId", order.getId());
            put("amount", order.getAmount());
        }};
    }

    @Override
    public OrderDetailDTO getOrderDetail(String orderId) {
        Order order = getOrderById(orderId);
        OrderDetailDTO detailDTO = new OrderDetailDTO();
        BeanUtils.copyProperties(order, detailDTO);
        
        // 设置房源信息
        House house = houseService.getById(order.getHouseId());
        OrderDetailDTO.HouseInfo houseInfo = new OrderDetailDTO.HouseInfo();
        BeanUtils.copyProperties(house, houseInfo);
        detailDTO.setHouse(houseInfo);
        
        return detailDTO;
    }

    @Override
    public Page<?> getOrderList(Integer currentPage, Integer pageSize, Integer status) {
        Page<Order> page = new Page<>(currentPage, pageSize);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getUserId, getCurrentUserId())
                .eq(status != null, Order::getStatus, status)
                .orderByDesc(Order::getCreateTime);
                
        return orderMapper.selectPage(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(String orderId) {
        Order order = getOrderById(orderId);
        if (order.getStatus() != BookingConstants.Status.PENDING_PAYMENT) {
            throw new BusinessException("只能取消待支付的订单");
        }
        
        order.setStatus(BookingConstants.Status.CANCELLED);
        orderMapper.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyRefund(String orderId) {
        Order order = getOrderById(orderId);
        if (order.getStatus() != BookingConstants.Status.PAID) {
            throw new BusinessException("只能申请已支付订单的退款");
        }
        
        paymentService.applyRefund(orderId, "用户申请退款");
    }
    
    private Order getOrderById(String orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST.getCode(), "订单不存在");
        }
        if (!order.getUserId().equals(getCurrentUserId())) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }
        return order;
    }
    
    private Long getCurrentUserId() {
        return securityUtil.getCurrentUserId();
    }
} 