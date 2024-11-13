package com.homestay.modules.auth.service;

import com.homestay.modules.auth.dto.AdminRegisterDTO;
import com.homestay.modules.auth.dto.LoginDTO;
import com.homestay.modules.auth.dto.MerchantRegisterDTO;
import com.homestay.modules.auth.dto.NormalUserRegisterDTO;
import com.homestay.modules.auth.vo.LoginVO;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface AuthService {
    LoginVO login(LoginDTO loginDTO);
    void register(NormalUserRegisterDTO normalUserRegisterDTO);
    void sendVerifyCode(String phone) throws MessagingException, UnsupportedEncodingException;
    boolean resetPassword(String email, String code, String newPassword);
    void logout();
    void registerMerchant(MerchantRegisterDTO merchantRegisterDTO);
    void registerAdmin(AdminRegisterDTO adminRegisterDTO);
} 