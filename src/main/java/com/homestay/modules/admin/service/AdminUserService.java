package com.homestay.modules.admin.service;

import com.homestay.modules.admin.dto.UserPageDTO;

public interface AdminUserService {
    
    UserPageDTO getUserList(Integer currentPage, Integer pageSize, String username, String phone, Integer status);
    
    void updateUserStatus(Long id, Integer status);
    
    void deleteUser(Long id);
} 