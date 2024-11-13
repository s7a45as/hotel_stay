package com.homestay.modules.admin.service;

import com.homestay.modules.admin.dto.AdminAuditDTO;
import com.homestay.modules.admin.dto.UserPageDTO;
import com.homestay.modules.admin.vo.AdminUserVO;
import java.util.List;

public interface AdminUserService {
    /**
     * 审核管理员
     */
    void auditAdmin(Long id, AdminAuditDTO auditDTO);

    /**
     * 获取待审核管理员列表
     */
    List<AdminUserVO> getPendingAdmins();

    /**
     * 获取用户列表（包括普通用户和商家）
     */
    UserPageDTO getUserList(Integer currentPage, Integer pageSize, String username, String phone, Integer status);

    /**
     * 更新用户状态
     */
    void updateUserStatus(Long id, Integer status);

    /**
     * 删除用户
     */
    void deleteUser(Long id);
} 