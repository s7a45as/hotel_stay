package com.homestay.modules.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.homestay.common.constant.CommonConstants;
import com.homestay.common.exception.BusinessException;
import com.homestay.common.response.ResultCode;
import com.homestay.modules.auth.dto.AdminRegisterDTO;
import com.homestay.modules.auth.dto.LoginDTO;
import com.homestay.modules.auth.dto.MerchantRegisterDTO;
import com.homestay.modules.auth.dto.NormalUserRegisterDTO;
import com.homestay.modules.auth.entity.AdminUser;
import com.homestay.modules.auth.entity.AuthMerchant;
import com.homestay.modules.auth.entity.BaseUser;
import com.homestay.modules.auth.entity.NormalUser;
import com.homestay.modules.auth.mapper.AdminMapper;
import com.homestay.modules.auth.mapper.AuthMerchantMapper;
import com.homestay.modules.auth.mapper.NormalUserMapper;
import com.homestay.modules.auth.service.AuthService;
import com.homestay.modules.auth.vo.LoginVO;
import com.homestay.utils.EmailUtil;
import com.homestay.utils.JwtUtil;
import com.homestay.utils.RedisUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AdminMapper adminMapper;
    private final NormalUserMapper normalUserMapper;
    private final AuthMerchantMapper authMerchantMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final EmailUtil emailUtil;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        LoginVO loginVO = new LoginVO();
        log.info("用户类型: {}", loginDTO.getType());
        //输出loginDTO
        log.info("用户登录信息: {}", loginDTO);
        switch (loginDTO.getType()) {
            case "merchant" -> {
                // 商家登录
                AuthMerchant authMerchant = getMerchantByUsername(loginDTO.getUsername());
                validateLogin(authMerchant, loginDTO.getPassword());
                validateMerchantStatus(authMerchant);
                loginVO = createLoginResponse(authMerchant, CommonConstants.System.MERCHANT_ROLE);
                log.info("商家登录");
            }
            case "admin" -> {
                // 管理员登录
                AdminUser admin = getAdminByUsername(loginDTO.getUsername());
                validateLogin(admin, loginDTO.getPassword());
                loginVO = createLoginResponse(admin, CommonConstants.System.ADMIN_ROLE);
                log.info("管理员登录");
            }
            case "user" -> {
                // 普通用户登录
                NormalUser user = getNormalUserByUsername(loginDTO.getUsername());
                validateLogin(user, loginDTO.getPassword());
                loginVO = createLoginResponse(user, CommonConstants.System.DEFAULT_ROLE);
                log.info("普通用户登录");
            }
            default -> throw new BusinessException(ResultCode.INVALID_LOGIN_TYPE);
        }
        
        return loginVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(NormalUserRegisterDTO normalUserRegisterDTO) {
        // 验证用户名和邮箱是否已存在
        validateNormalUserRegisterInfo(normalUserRegisterDTO);
        
        // 创建并保存用户
        NormalUser user = new NormalUser();
        BeanUtils.copyProperties(normalUserRegisterDTO, user);
        user.setPassword(passwordEncoder.encode(normalUserRegisterDTO.getPassword()));
        user.setStatus(CommonConstants.System.NORMAL_STATUS);
        
        normalUserMapper.insert(user);
        log.info("用户注册成功: {}", normalUserRegisterDTO.getUsername());
    }

    @Override
    public void sendVerifyCode(String email) throws MessagingException, UnsupportedEncodingException {
        checkVerifyCodeFrequency(email);
        String code = getOrGenerateVerifyCode(email);
        emailUtil.sendVerificationCode(email, code, "5分钟");
        log.info("验证码发送成功: {}", email);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerMerchant(MerchantRegisterDTO dto) {
        validateVerifyCode(dto.getEmail(), dto.getVerifyCode());
        validateMerchantRegisterInfo(dto);
        
        // 创建商家用户
        AuthMerchant authMerchant = new AuthMerchant();
        BeanUtils.copyProperties(dto, authMerchant);
        authMerchant.setPassword(passwordEncoder.encode(dto.getPassword()));
        authMerchant.setStatus(CommonConstants.System.PENDING_STATUS);
        
        authMerchantMapper.insert(authMerchant);
        
        // 发送审核通知邮件
        sendMerchantRegisterNotification(dto);
        
        log.info("商家注册成功，等待审核: {}", dto.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(String email, String code, String newPassword) {
        validateVerifyCode(email, code);
        
        // 尝试在所有用户表中查找并更新密码
        boolean updated = updatePasswordIfExists(email, newPassword);
        if (!updated) {
            throw new BusinessException(ResultCode.EMAIL_NOT_EXIST);
        }
        
        clearVerifyCode(email);
        log.info("密码重置成功: {}", email);
        return true;
    }

    @Override
    public void logout() {
        // TODO: 实现以下功能
        // 1. 获取当前用户token
        // 2. 将token加入黑名单
        // 3. 清除用户相关的缓存
        // 4. 记录登出日志
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerAdmin(AdminRegisterDTO adminRegisterDTO) {
        // 验证验证码
        validateVerifyCode(adminRegisterDTO.getEmail(), adminRegisterDTO.getVerifyCode());
        
        // 验证用户名、邮箱、手机号是否已存在
        if (existsUsername(adminRegisterDTO.getUsername())) {
            throw new BusinessException(ResultCode.USERNAME_EXIST);
        }
        if (existsEmail(adminRegisterDTO.getEmail())) {
            throw new BusinessException(ResultCode.EMAIL_EXIST);
        }
        if (existsPhone(adminRegisterDTO.getPhone())) {
            throw new BusinessException(ResultCode.PHONE_EXIST);
        }
        
        // 创建管理员用户
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(adminRegisterDTO, adminUser);
        
        // 加密密码
        adminUser.setPassword(passwordEncoder.encode(adminRegisterDTO.getPassword()));
        
        // 设置状态为待审核
        adminUser.setStatus(CommonConstants.System.PENDING_STATUS);
        
        // 保存到数据库
        adminMapper.insert(adminUser);
        
        // 发送审核通知邮件给超级管理员
        sendAdminRegisterNotification(adminRegisterDTO);
        
        log.info("管理员注册成功，等待审核: {}", adminRegisterDTO.getUsername());
    }

    // 私有辅助方法
    private AuthMerchant getMerchantByUsername(String username) {
        AuthMerchant authMerchant = authMerchantMapper.selectOne(new LambdaQueryWrapper<AuthMerchant>()
                .eq(AuthMerchant::getUsername, username));
        if (authMerchant == null) {
            throw new BusinessException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }
        return authMerchant;
    }

    private AdminUser getAdminByUsername(String username) {
        AdminUser admin = adminMapper.selectOne(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getUsername, username));
        if (admin == null) {
            throw new BusinessException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }
        return admin;
    }

    private NormalUser getNormalUserByUsername(String username) {
        NormalUser user = normalUserMapper.selectOne(new LambdaQueryWrapper<NormalUser>()
                .eq(NormalUser::getUsername, username));
        if (user == null) {
            throw new BusinessException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }
        return user;
    }

    private void validateLogin(BaseUser user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }
        if (user.getStatus() != CommonConstants.System.NORMAL_STATUS) {
            throw new BusinessException(ResultCode.ACCOUNT_DISABLED);
        }
    }

    private void validateMerchantStatus(AuthMerchant authMerchant) {
        switch (authMerchant.getStatus()) {
            case CommonConstants.System.PENDING_STATUS -> 
                throw new BusinessException(ResultCode.MERCHANT_PENDING);
            case CommonConstants.System.DISABLED_STATUS -> 
                throw new BusinessException(ResultCode.MERCHANT_DISABLED);
            case CommonConstants.System.NORMAL_STATUS -> {
                // 正常状态，不做处理
            }
            default -> throw new BusinessException(ResultCode.MERCHANT_STATUS_ERROR);
        }
    }

    private LoginVO createLoginResponse(BaseUser user, String role) {
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(jwtUtil.generateToken(user.getId()));
        
        LoginVO.UserInfo userInfo = new LoginVO.UserInfo();
        BeanUtils.copyProperties(user, userInfo);
        userInfo.setRole(role);
        
        if (user instanceof AuthMerchant authMerchant) {
            userInfo.setBusinessName(authMerchant.getBusinessName());
            userInfo.setBusinessAddress(authMerchant.getBusinessAddress());
        }
        
        loginVO.setUserInfo(userInfo);
        return loginVO;
    }

    private void validateNormalUserRegisterInfo(NormalUserRegisterDTO dto) {
        if (existsUsername(dto.getUsername())) {
            throw new BusinessException(ResultCode.USERNAME_EXIST);
        }
        if (existsEmail(dto.getEmail())) {
            throw new BusinessException(ResultCode.EMAIL_EXIST);
        }
    }

    private void validateMerchantRegisterInfo(MerchantRegisterDTO dto) {

        if (existsBusinessLicense(dto.getBusinessLicense())) {
            throw new BusinessException(ResultCode.BUSINESS_LICENSE_EXIST);
        }
    }

    private boolean existsUsername(String username) {
        return normalUserMapper.exists(new LambdaQueryWrapper<NormalUser>()
                .eq(NormalUser::getUsername, username)) ||
               authMerchantMapper.exists(new LambdaQueryWrapper<AuthMerchant>()
                .eq(AuthMerchant::getUsername, username)) ||
               adminMapper.exists(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getUsername, username));
    }

    private boolean existsEmail(String email) {
        return normalUserMapper.exists(new LambdaQueryWrapper<NormalUser>()
                .eq(NormalUser::getEmail, email)) ||
               authMerchantMapper.exists(new LambdaQueryWrapper<AuthMerchant>()
                .eq(AuthMerchant::getEmail, email)) ||
               adminMapper.exists(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getEmail, email));
    }

    private boolean existsBusinessLicense(String license) {
        return authMerchantMapper.exists(new LambdaQueryWrapper<AuthMerchant>()
                .eq(AuthMerchant::getBusinessLicense, license));
    }

    private boolean updatePasswordIfExists(String email, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        int count = 0;
        
        // 更新普通用户密码
        NormalUser normalUser = new NormalUser();
        normalUser.setPassword(encodedPassword);
        count += normalUserMapper.update(normalUser, new LambdaQueryWrapper<NormalUser>()
                .eq(NormalUser::getEmail, email));
                
        // 更新商家密码
        AuthMerchant authMerchant = new AuthMerchant();
        authMerchant.setPassword(encodedPassword);
        count += authMerchantMapper.update(authMerchant, new LambdaQueryWrapper<AuthMerchant>()
                .eq(AuthMerchant::getEmail, email));
                
        // 更新管理员密码
        AdminUser adminUser = new AdminUser();
        adminUser.setPassword(encodedPassword);
        count += adminMapper.update(adminUser, new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getEmail, email));
                
        return count > 0;
    }

    /**
     * 检查验证码发送频率
     */
    private void checkVerifyCodeFrequency(String email) {
        String key = CommonConstants.Redis.VERIFY_CODE_PREFIX + email;
        String existingCode = (String) redisUtil.get(key);
        if (existingCode != null) {
            long ttl = redisUtil.getExpire(key);
            // 如果验证码还有4分钟以上有效期，说明刚发送过
            if (ttl > 240) {
                throw new BusinessException(ResultCode.VERIFY_CODE_FREQUENT);
            }
        }
    }

    /**
     * 获取或生成验证码
     */
    private String getOrGenerateVerifyCode(String email) {
        String key = CommonConstants.Redis.VERIFY_CODE_PREFIX + email;
        String existingCode = (String) redisUtil.get(key);
        
        // 如果验证码存在且未过期，继续使用
        if (existingCode != null && redisUtil.getExpire(key) > 0) {
            return existingCode;
        }
        
        // 生成新的6位数字验证码
        String code = String.format("%06d", (int)(Math.random() * 1000000));
        redisUtil.setEx(key, code, CommonConstants.Redis.VERIFY_CODE_EXPIRE);
        return code;
    }

    /**
     * 验证验证码
     */
    private void validateVerifyCode(String email, String code) {
        String key = CommonConstants.Redis.VERIFY_CODE_PREFIX + email;
        String savedCode = (String) redisUtil.get(key);
        if (savedCode == null) {
            throw new BusinessException(ResultCode.VERIFY_CODE_EXPIRED);
        }
        if (!savedCode.equals(code)) {
            throw new BusinessException(ResultCode.VERIFY_CODE_ERROR);
        }
    }

    /**
     * 清除验证码
     */
    private void clearVerifyCode(String email) {
        String key = CommonConstants.Redis.VERIFY_CODE_PREFIX + email;
        redisUtil.delete(key);
    }

    /**
     * 发送商家注册通知邮件给管理员
     */
    private void sendMerchantRegisterNotification(MerchantRegisterDTO dto) {
        try {
            String content = String.format("""
                <div style="padding: 20px; background-color: #f8f9fa; border-radius: 5px;">
                    <h3 style="color: #1a73e8;">新商家注册通知</h3>
                    <p><strong>商家名称：</strong>%s</p>
                    <p><strong>联系人：</strong>%s</p>
                    <p><strong>联系电话：</strong>%s</p>
                    <p><strong>联系邮箱：</strong>%s</p>
                    <p><strong>营业执照号：</strong>%s</p>
                    <p><strong>商家地址：</strong>%s</p>
                    <div style="margin-top: 20px; padding: 15px; background-color: #fff3cd; border-radius: 4px;">
                        <p style="color: #856404; margin: 0;">请尽快审核该商家的注册申请！</p>
                    </div>
                </div>
                """,
                dto.getBusinessName(),
                dto.getContactPerson(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getBusinessLicense(),
                dto.getBusinessAddress()
            );
            
            emailUtil.sendVerificationCode(
                CommonConstants.System.ADMIN_EMAIL,
                "新商家注册待审核 - " + dto.getBusinessName(),
                content
            );
            
            log.info("商家注册通知邮件发送成功: {}", dto.getBusinessName());
        } catch (Exception e) {
            log.error("商家注册通知邮件发送失败: {}", dto.getBusinessName(), e);
            // 不抛出异常，避免影响主流程
        }
    }

    /**
     * 验证商家信息
     */
    private void validateMerchantInfo(AuthMerchant authMerchant) {
        // 验证必要的商家信息
        if (authMerchant.getBusinessName() == null || authMerchant.getBusinessName().trim().isEmpty()) {
            throw new BusinessException(ResultCode.INVALID_MERCHANT_INFO, "商家名称不能为空");
        }
        if (authMerchant.getBusinessLicense() == null || authMerchant.getBusinessLicense().trim().isEmpty()) {
            throw new BusinessException(ResultCode.INVALID_MERCHANT_INFO, "营业执照号不能为空");
        }
        if (authMerchant.getBusinessAddress() == null || authMerchant.getBusinessAddress().trim().isEmpty()) {
            throw new BusinessException(ResultCode.INVALID_MERCHANT_INFO, "商家地址不能为空");
        }
        if (authMerchant.getContactPerson() == null || authMerchant.getContactPerson().trim().isEmpty()) {
            throw new BusinessException(ResultCode.INVALID_MERCHANT_INFO, "联系人不能为空");
        }
    }

    /**
     * 检查用户状态
     */
    private void checkUserStatus(BaseUser user) {
        if (user.getStatus() == null) {
            throw new BusinessException(ResultCode.ACCOUNT_ERROR, "账号状态异常");
        }
        switch (user.getStatus()) {
            case CommonConstants.System.NORMAL_STATUS -> {
                // 正常状态，不做处理
            }
            case CommonConstants.System.DISABLED_STATUS -> 
                throw new BusinessException(ResultCode.ACCOUNT_DISABLED);
            case CommonConstants.System.PENDING_STATUS -> 
                throw new BusinessException(ResultCode.ACCOUNT_PENDING);
            default -> throw new BusinessException(ResultCode.ACCOUNT_ERROR);
        }
    }

    /**
     * 检查手机号是否已存在
     */
    private boolean existsPhone(String phone) {
        return normalUserMapper.exists(new LambdaQueryWrapper<NormalUser>()
                .eq(NormalUser::getPhone, phone)) ||
               authMerchantMapper.exists(new LambdaQueryWrapper<AuthMerchant>()
                .eq(AuthMerchant::getPhone, phone)) ||
               adminMapper.exists(new LambdaQueryWrapper<AdminUser>()
                .eq(AdminUser::getPhone, phone));
    }

    /**
     * 发送管理员注册通知邮件
     */
    private void sendAdminRegisterNotification(AdminRegisterDTO dto) {
        try {
            String content = String.format("""
                <div style="padding: 20px; background-color: #f8f9fa; border-radius: 5px;">
                    <h3 style="color: #1a73e8;">新管理员注册通知</h3>
                    <p><strong>用户名：</strong>%s</p>
                    <p><strong>昵称：</strong>%s</p>
                    <p><strong>邮箱：</strong>%s</p>
                    <p><strong>手机号：</strong>%s</p>
                    <p><strong>部门：</strong>%s</p>
                    <p><strong>职位：</strong>%s</p>
                    <div style="margin-top: 20px; padding: 15px; background-color: #fff3cd; border-radius: 4px;">
                        <p style="color: #856404; margin: 0;">请尽快审核该管理员的注册申请！</p>
                    </div>
                </div>
                """,
                dto.getUsername(),
                dto.getNickname(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getDepartment(),
                dto.getPosition()
            );
            
            emailUtil.sendVerificationCode(
                CommonConstants.System.ADMIN_EMAIL,
                "新管理员注册待审核 - " + dto.getUsername(),
                content
            );
            
            log.info("管理员注册通知邮件发送成功: {}", dto.getUsername());
        } catch (Exception e) {
            log.error("管理员注册通知邮件发送失败: {}", dto.getUsername(), e);
            // 不抛出异常，避免影响主流程
        }
    }
} 