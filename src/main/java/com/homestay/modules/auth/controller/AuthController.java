package com.homestay.modules.auth.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.auth.dto.*;
import com.homestay.modules.auth.service.AuthService;
import com.homestay.modules.auth.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@Tag(name = "认证管理", description = "认证相关接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "holle";
    }

    @Operation(summary = "用户登录", description = "根据不同角色登录系统")
    @ApiResponse(responseCode = "200", description = "登录成功")
    @PostMapping("/login")
    public Result<LoginVO> login(@Validated @RequestBody LoginDTO loginDTO) {
        return Result.success(authService.login(loginDTO));
    }

    @Operation(summary = "用户注册", description = "注册新用户")
    @ApiResponse(responseCode = "200", description = "注册成功")
    @PostMapping("/register/user")
    public Result<Void> register(@Validated @RequestBody NormalUserRegisterDTO normalUserRegisterDTO) {
        authService.register(normalUserRegisterDTO);
        return Result.success();
    }

    @Operation(summary = "发送验证码", description = "发送邮箱验证码")
    @ApiResponse(responseCode = "200", description = "发送成功")
    @PostMapping("/register/verify-code")
    public Result<Void> sendVerifyCode(@RequestBody @Validated EmailDTO emailDTO) throws MessagingException, UnsupportedEncodingException {
        authService.sendVerifyCode(emailDTO.getEmail());
        return Result.success();
    }

    @Operation(summary = "重置密码", description = "通过邮箱验证码重置密码")
    @ApiResponse(responseCode = "200", description = "重置成功")
    @PostMapping("/reset")
    public Result<Boolean> resetPassword(@RequestBody @Validated ResetPasswordDTO resetPasswordDTO) {
        return Result.success(authService.resetPassword(
            resetPasswordDTO.getEmail(), 
            resetPasswordDTO.getCode(), 
            resetPasswordDTO.getNewPassword()
        ));
    }

    @Operation(summary = "退出登录", description = "退出系统")
    @ApiResponse(responseCode = "200", description = "退出成功")
    @PostMapping("/logout")
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }

    @Operation(summary = "商家注册", description = "注册新商家")
    @ApiResponse(responseCode = "200", description = "注册成功")
    @PostMapping("/register/merchant")
    public Result<Void> registerMerchant(@Validated @RequestBody MerchantRegisterDTO merchantRegisterDTO) {
        authService.registerMerchant(merchantRegisterDTO);
        return Result.success();
    }

    //管理员注册
    @Operation(summary = "管理员注册", description = "注册新管理员")
    @ApiResponse(responseCode = "200", description = "注册成功")
    @PostMapping("/register/admin")
    public Result<Void> registerAdmin(@Validated @RequestBody AdminRegisterDTO adminRegisterDTO) {
        authService.registerAdmin(adminRegisterDTO);
        return Result.success();
    }

} 