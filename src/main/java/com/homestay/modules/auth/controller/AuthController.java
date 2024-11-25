package com.homestay.modules.auth.controller;

import com.homestay.common.response.Result;
import com.homestay.modules.auth.dto.*;
import com.homestay.modules.auth.service.AuthService;
import com.homestay.modules.auth.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@Tag(name = "认证管理", description = "提供用户认证相关功能，包括登录、注册、重置密码等")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "用户登录", 
              description = "支持普通用户、商家、管理员三种角色的登录")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "登录成功",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = LoginVO.class))),
        @ApiResponse(responseCode = "400", description = "参数错误"),
        @ApiResponse(responseCode = "401", description = "用户名或密码错误"),
        @ApiResponse(responseCode = "403", description = "账号已被禁用")
    })
    @PostMapping("/login")
    public Result<LoginVO> login(
        @Parameter(description = "登录信息", required = true)
        @Validated @RequestBody LoginDTO loginDTO
    ) {
        return Result.success(authService.login(loginDTO));
    }

    @Operation(summary = "用户注册", 
              description = "注册普通用户账号")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "注册成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "409", description = "用户名或邮箱已存在")
    })
    @PostMapping("/register/user")
    public Result<Void> register(
        @Parameter(description = "用户注册信息", required = true)
        @Validated @RequestBody NormalUserRegisterDTO normalUserRegisterDTO
    ) {
        authService.register(normalUserRegisterDTO);
        return Result.success();
    }

    @Operation(summary = "发送验证码", 
              description = "发送邮箱验证码，用于注册或重置密码，5分钟内有效")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "发送成功"),
        @ApiResponse(responseCode = "400", description = "邮箱格式错误"),
        @ApiResponse(responseCode = "429", description = "发送频率过快，请稍后再试")
    })
    @PostMapping("/register/verify-code")
    public Result<Void> sendVerifyCode(
        @Parameter(description = "邮箱信息", required = true)
        @RequestBody @Validated EmailDTO emailDTO
    ) throws MessagingException, UnsupportedEncodingException {
        authService.sendVerifyCode(emailDTO.getEmail());
        return Result.success();
    }

    @Operation(summary = "重置密码", 
              description = "通过邮箱验证码重置密码")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "重置成功"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "验证码错误或已过期"),
        @ApiResponse(responseCode = "404", description = "邮箱不存在")
    })
    @PostMapping("/reset")
    public Result<Boolean> resetPassword(
        @Parameter(description = "重置密码信息", required = true)
        @RequestBody @Validated ResetPasswordDTO resetPasswordDTO
    ) {
        return Result.success(authService.resetPassword(
            resetPasswordDTO.getEmail(), 
            resetPasswordDTO.getCode(), 
            resetPasswordDTO.getNewPassword()
        ));
    }

    @Operation(summary = "退出登录", 
              description = "清除用户登录状态，使当前token失效")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "退出成功"),
        @ApiResponse(responseCode = "401", description = "未登录或token已失效")
    })
    @PostMapping("/logout")
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }

    @Operation(summary = "商家注册", 
              description = "注册商家账号，需要等待管理员审核")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "注册成功，等待审核"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "验证码错误或已过期"),
        @ApiResponse(responseCode = "409", description = "用户名、邮箱或营业执照号已存在")
    })
    @PostMapping("/register/merchant")
    public Result<Void> registerMerchant(
        @Parameter(description = "商家注册信息", required = true)
        @Validated @RequestBody MerchantRegisterDTO merchantRegisterDTO
    ) {
        authService.registerMerchant(merchantRegisterDTO);
        return Result.success();
    }

    @Operation(summary = "管理员注册", 
              description = "注册管理员账号，需要等待超级管理员审核")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "注册成功，等待审核"),
        @ApiResponse(responseCode = "400", description = "参数校验失败"),
        @ApiResponse(responseCode = "401", description = "验证码错误或已过期"),
        @ApiResponse(responseCode = "409", description = "用户名、邮箱或手机号已存在")
    })
    @PostMapping("/register/admin")
    public Result<Void> registerAdmin(
        @Parameter(description = "管理员注册信息", required = true)
        @Validated @RequestBody AdminRegisterDTO adminRegisterDTO
    ) {
        authService.registerAdmin(adminRegisterDTO);
        return Result.success();
    }
} 