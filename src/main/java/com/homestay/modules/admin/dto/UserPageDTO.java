package com.homestay.modules.admin.dto;

import com.homestay.modules.auth.entity.BaseUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "用户分页数据")
public class UserPageDTO {
    
    @Schema(description = "总记录数")
    private Long total;
    
    @Schema(description = "当前页码")
    private Integer currentPage;
    
    @Schema(description = "每页记录数")
    private Integer pageSize;
    
    @Schema(description = "用户列表")
    private List<BaseUser> list;
} 