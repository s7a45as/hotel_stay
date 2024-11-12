package com.homestay.modules.admin.dto;

import com.homestay.modules.admin.entity.adminOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "订单分页数据")
public class OrderPageDTO {
    
    @Schema(description = "总记录数")
    private Long total;
    
    @Schema(description = "当前页码")
    private Integer currentPage;
    
    @Schema(description = "每页记录数")
    private Integer pageSize;
    
    @Schema(description = "订单列表")
    private List<adminOrder> list;
} 