package com.homestay.modules.admin.service;

import com.homestay.modules.admin.dto.AdminAuditDTO;
import com.homestay.modules.admin.dto.UpdatePasswordDTO;
import com.homestay.modules.admin.dto.UpdateUserDTO;
import com.homestay.modules.admin.dto.UserPageDTO;
import com.homestay.modules.admin.vo.AdminUserVO;

import java.util.List;

/**
 * 管理员用户服务接口
 */
public interface AdminUserService {

    /**
     * 获取用户列表
     * @param currentPage 当前页码
     * @param pageSize 每页数量
     * @param username 用户名
     * @param phone 手机号
     * @param status 状态
     * @return 用户分页数据
     */
    UserPageDTO getUserList(Integer currentPage, Integer pageSize, String username, String phone, Integer status);

    /**
     * 更新用户信息
     * @param id 用户ID
     * @param updateUserDTO 用户信息
     */
    void updateUser(Long id, UpdateUserDTO updateUserDTO);

    /**
     * 修改用户密码
     * @param id 用户ID
     * @param passwordDTO 密码信息
     */
    void updateUserPassword(Long id, UpdatePasswordDTO passwordDTO);

    /**
     * 更新用户状态
     * @param id 用户ID
     * @param status 状态值
     */
    void updateUserStatus(Long id, Integer status);

    /**
     * 审核管理员
     * @param id 管理员ID
     * @param auditDTO 审核信息
     */
    void auditAdmin(Long id, AdminAuditDTO auditDTO);

    /**
     * 获取待审核管理员列表
     * @return 待审核管理员列表
     */
    List<AdminUserVO> getPendingAdmins();

    /**
     * 删除用户
     * @param id 用户ID
     */
    void deleteUser(Long id);
} 