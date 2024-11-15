package com.homestay.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.exception.BusinessException;
import com.homestay.modules.admin.dto.OrderPageDTO;
import com.homestay.modules.admin.entity.adminOrder;
import com.homestay.modules.admin.mapper.AdminOrderMapper;
import com.homestay.modules.admin.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminOrderServiceImpl implements AdminOrderService {

    private final AdminOrderMapper adminOrderMapper;

    @Override
    public OrderPageDTO getOrderList(Integer currentPage, Integer pageSize, String orderId, String userName, String status) {
        Page<adminOrder> page = new Page<>(currentPage, pageSize);
        
        LambdaQueryWrapper<adminOrder> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(orderId)) {
            wrapper.eq(adminOrder::getId, orderId);
        }
        if (StringUtils.hasText(userName)) {
            wrapper.like(adminOrder::getUserName, userName);
        }
        if (StringUtils.hasText(status)) {
            wrapper.eq(adminOrder::getStatus, status);
        }
        wrapper.orderByDesc(adminOrder::getCreateTime);
        
        Page<adminOrder> orderPage = adminOrderMapper.selectPage(page, wrapper);
        
        return OrderPageDTO.builder()
                .total(orderPage.getTotal())
                .currentPage(currentPage)
                .pageSize(pageSize)
                .list(orderPage.getRecords())
                .build();
    }

    @Override
    public void updateOrderStatus(String id, String status) {
        adminOrder adminOrder = adminOrderMapper.selectById(id);
        if (adminOrder == null) {
            throw new BusinessException("订单不存在");
        }
        
        adminOrder updateAdminOrder = new adminOrder();
        updateAdminOrder.setId(id);
        updateAdminOrder.setStatus(status);
        adminOrderMapper.updateById(updateAdminOrder);
    }

    @Override
    public void deleteOrder(String id) {
        adminOrderMapper.deleteById(id);
    }

    @Override
    public byte[] exportOrders(String orderId, String userName, String status) {
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("订单列表");
            
            // 创建表头
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "订单ID", "房源名称", "用户姓名", "联系电话", 
                "入住日期", "离店日期", "入住天数", "入住人数",
                "订单金额", "订单状态", "创建时间", "支付时间"
            };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            
            // 查询数据
            LambdaQueryWrapper<adminOrder> wrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(orderId)) {
                wrapper.like(adminOrder::getId, orderId);
            }
            if (StringUtils.hasText(userName)) {
                wrapper.like(adminOrder::getUserName, userName);
            }
            if (StringUtils.hasText(status)) {
                wrapper.eq(adminOrder::getStatus, status);
            }
            wrapper.eq(adminOrder::getDeleted, 0); // 只导出未删除的订单
            
            List<adminOrder> orders = adminOrderMapper.selectList(wrapper);
            
            // 填充数据
            for (int i = 0; i < orders.size(); i++) {
                adminOrder order = orders.get(i);
                Row row = sheet.createRow(i + 1);
                
                row.createCell(0).setCellValue(order.getId());
                row.createCell(1).setCellValue(order.getHouseName());
                row.createCell(2).setCellValue(order.getUserName());
                row.createCell(3).setCellValue(order.getPhone());
                row.createCell(4).setCellValue(
                    order.getCheckIn() != null ? 
                    order.getCheckIn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : ""
                );
                row.createCell(5).setCellValue(
                    order.getCheckOut() != null ? 
                    order.getCheckOut().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : ""
                );
                row.createCell(6).setCellValue(order.getNights() != null ? order.getNights() : 0);
                row.createCell(7).setCellValue(order.getGuests() != null ? order.getGuests() : 0);
                row.createCell(8).setCellValue(
                    order.getTotalAmount() != null ? order.getTotalAmount().doubleValue() : 0.00
                );
                row.createCell(9).setCellValue(getOrderStatusText(order.getStatus()));
                row.createCell(10).setCellValue(
                    order.getCreateTime() != null ? 
                    order.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : ""
                );
                row.createCell(11).setCellValue(
                    order.getPayTime() != null ? 
                    order.getPayTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : ""
                );
            }
            
            // 自动调整列宽
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
                // 设置最小列宽
                int width = sheet.getColumnWidth(i);
                if (width < 3000) {
                    sheet.setColumnWidth(i, 3000);
                }
            }
            
            // 输出为字节数组
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
            
        } catch (Exception e) {
            log.error("导出订单数据失败", e);
            throw new BusinessException("导出失败：" + e.getMessage());
        }
    }

    /**
     * 获取订单状态文本
     */
    private String getOrderStatusText(String status) {
        if (status == null) return "";
        return switch (status) {
            case "PENDING" -> "待支付";
            case "PAID" -> "已支付";
            case "COMPLETED" -> "已完成";
            case "CANCELLED" -> "已取消";
            case "REFUNDED" -> "已退款";
            default -> status;
        };
    }
} 