package com.homestay.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.shareentity.House;
import com.homestay.modules.admin.dto.HouseCreateDTO;
import com.homestay.modules.admin.dto.HousePageDTO;
import com.homestay.modules.admin.dto.HouseUpdateDTO;
import com.homestay.modules.admin.mapper.AdminHouseMapper;
import com.homestay.modules.admin.service.AdminHouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminHouseServiceImpl implements AdminHouseService {

    private final AdminHouseMapper adminHouseMapper;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public HousePageDTO getHouseList(Integer currentPage, Integer pageSize, String name, String type, Integer status) {
        Page<House> page = new Page<>(currentPage, pageSize);

        LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(name)) {
            wrapper.like(House::getName, name);
        }
        if (StringUtils.hasText(type)) {
            wrapper.eq(House::getType, type);
        }
        if (status != null) {
            wrapper.eq(House::getStatus, status);
        }

        Page<House> housePage = adminHouseMapper.selectPage(page, wrapper);

        return HousePageDTO.builder()
                .total(housePage.getTotal())
                .currentPage(currentPage)
                .pageSize(pageSize)
                .list(housePage.getRecords())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createHouse(HouseCreateDTO houseDTO) {
        House house = new House();
        BeanUtils.copyProperties(houseDTO, house);
        adminHouseMapper.insert(house);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHouse(Long id, HouseUpdateDTO houseDTO) {
        House house = adminHouseMapper.selectById(id);
        if (house == null) {
            throw new BusinessException("房源不存在");
        }

        BeanUtils.copyProperties(houseDTO, house);
        adminHouseMapper.updateById(house);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteHouse(Long id) {
        int count = adminHouseMapper.deleteById(id);
        if (count == 0) {
            throw new BusinessException("房源不存在");
        }
    }

    @Override
    public byte[] exportHouses(String name, String type, Integer status) {
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("房源列表");
            
            // 创建表头
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "名称", "类型", "价格", "地址", "状态", "创建时间"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            
            // 查询数据
            LambdaQueryWrapper<House> wrapper = new LambdaQueryWrapper<>();
            if (StringUtils.hasText(name)) {
                wrapper.like(House::getName, name);
            }
            if (StringUtils.hasText(type)) {
                wrapper.eq(House::getType, type);
            }
            if (status != null) {
                wrapper.eq(House::getStatus, status);
            }
            
            List<House> houses = adminHouseMapper.selectList(wrapper);
            
            // 填充数据
            for (int i = 0; i < houses.size(); i++) {
                House house = houses.get(i);
                Row row = sheet.createRow(i + 1);
                
                // ID - 转为字符串
                Cell idCell = row.createCell(0);
                idCell.setCellValue(String.valueOf(house.getId()));
                
                // 名称
                Cell nameCell = row.createCell(1);
                nameCell.setCellValue(house.getName() != null ? house.getName() : "");
                
                // 类型
                Cell typeCell = row.createCell(2);
                typeCell.setCellValue(house.getType() != null ? house.getType() : "");
                
                // 价格 - 使用数值类型
                Cell priceCell = row.createCell(3);
                if (house.getPrice() != null) {
                    priceCell.setCellValue(house.getPrice().doubleValue());
                } else {
                    priceCell.setCellValue(0.0);
                }
                
                // 地址
                Cell addressCell = row.createCell(4);
                addressCell.setCellValue(house.getAddress() != null ? house.getAddress() : "");
                
                // 状态
                Cell statusCell = row.createCell(5);
                statusCell.setCellValue(getStatusText(house.getStatus()));
                
                // 创建时间
                Cell timeCell = row.createCell(6);
                timeCell.setCellValue(
                    house.getCreateTime() != null ? 
                    house.getCreateTime().format(DATE_FORMATTER) : ""
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
            log.error("导出房源数据失败", e);
            throw new BusinessException("导出失败：" + e.getMessage());
        }
    }

    /**
     * 获取状态文本
     */
    private String getStatusText(Integer status) {
        if (status == null) return "";
        return switch (status) {
            case 0 -> "下架";
            case 1 -> "上架";
            case 2 -> "待审核";
            default -> "未知";
        };
    }
} 