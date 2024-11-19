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
import com.homestay.common.utils.EmailUtil;
import com.homestay.common.utils.JwtUtil;
import com.homestay.common.utils.RedisUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.http.HttpServletRequest;

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
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${homestay.base-url}")
    private String baseUrl;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        LoginVO loginVO = new LoginVO();
        log.info("用户类型: {}", loginDTO.getType());
        log.info("用户登录信息: {}", loginDTO);
        
        switch (loginDTO.getType()) {
            case "merchant" -> {
                // 商家登录
                AuthMerchant authMerchant = getMerchantByUsername(loginDTO.getUsername());
                validateLogin(authMerchant, loginDTO.getPassword());
                validateMerchantStatus(authMerchant);
                loginVO = createLoginResponse(authMerchant, CommonConstants.System.MERCHANT_ROLE);
                // 存储token到Redis
                storeTokenInRedis(loginVO.getToken(), authMerchant.getId());
                log.info("商家登录");
            }
            case "admin" -> {
                // 管理员登录
                AdminUser admin = getAdminByUsername(loginDTO.getUsername());
                validateLogin(admin, loginDTO.getPassword());
                loginVO = createLoginResponse(admin, CommonConstants.System.ADMIN_ROLE);
                // 存储token到Redis
                storeTokenInRedis(loginVO.getToken(), admin.getId());
                log.info("管理员登录");
            }
            case "user" -> {
                // 普通用户登录
                NormalUser user = getNormalUserByUsername(loginDTO.getUsername());
                validateLogin(user, loginDTO.getPassword());
                loginVO = createLoginResponse(user, CommonConstants.System.DEFAULT_ROLE);
                // 存储token到Redis
                storeTokenInRedis(loginVO.getToken(), user.getId());
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
        
        // 先保存到数据库获取ID
        authMerchantMapper.insert(authMerchant);
        
        // 发送审核通知邮件
        sendMerchantRegisterNotification(authMerchant);
        
        log.info("商家注册成功，等待审核: {}", dto.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resetPassword(String email, String code, String newPassword) {
        validateVerifyCode(email, code);
        
        // 尝试在所有用户表中找并更新密码
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
        // 获取当前请求的token
        String token = getTokenFromRequest();
        if (token == null) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }
        
        // 将token加入黑名单
        String blacklistKey = "token:blacklist:" + token;
        redisTemplate.opsForValue().set(blacklistKey, true, 2, TimeUnit.DAYS);
        
        // 删除Redis中存储的token信息
        String tokenKey = CommonConstants.Redis.TOKEN_PREFIX + token;
        redisTemplate.delete(tokenKey);
        
        // 清除Security上下文
        SecurityContextHolder.clearContext();
        
        log.info("用户登出成功，token已加入黑名单");
    }

    /**
     * 从请求中获取token
     */
    private String getTokenFromRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new BusinessException(ResultCode.ERROR.getCode(), "获取请求上下文失败");
        }
        
        HttpServletRequest request = attributes.getRequest();
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith("Bearer ")) {
            return null;
        }
        
        return bearerToken.substring(7);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerAdmin(AdminRegisterDTO dto) {
        // 验证验证码
        validateVerifyCode(dto.getEmail(), dto.getVerifyCode());
        
        // 验证用户名、邮箱、手机号是否已存在
        if (existsUsername(dto.getUsername())) {
            throw new BusinessException(ResultCode.USERNAME_EXIST);
        }
        if (existsEmail(dto.getEmail())) {
            throw new BusinessException(ResultCode.EMAIL_EXIST);
        }
        if (existsPhone(dto.getPhone())) {
            throw new BusinessException(ResultCode.PHONE_EXIST);
        }
        
        // 创建管理员用户
        AdminUser adminUser = new AdminUser();
        BeanUtils.copyProperties(dto, adminUser);
        adminUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        adminUser.setStatus(CommonConstants.System.PENDING_STATUS);
        
        // 先保存到数据库获取ID
        adminMapper.insert(adminUser);
        
        // 发送审核通知邮件
        sendAdminRegisterNotification(adminUser);
        
        log.info("管理员注册成功，等待审核: {}", dto.getUsername());
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
        
        // 如果��证码在且未期，续使
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
     * 发送商家注册通邮件给管理员
     */
    private void sendMerchantRegisterNotification(AuthMerchant merchant) {
        try {
            String auditUrl = String.format("%s/admin/audit/merchant/%d?token=%s", 
                baseUrl, 
                merchant.getId(), 
                generateAuditToken(merchant.getId())
            );
            
            String content = String.format("""
                <h3 style="color: #1a73e8;">新商家注册通知</h3>
                <div style="margin: 20px 0;">
                    <p><strong>商家名称：</strong>%s</p>
                    <p><strong>联系人：</strong>%s</p>
                    <p><strong>联系电话：</strong>%s</p>
                    <p><strong>联系邮箱：</strong>%s</p>
                    <p><strong>营业执照号：</strong>%s</p>
                    <p><strong>商家地址：</strong>%s</p>
                </div>
                <div style="padding: 15px; background-color: #fff3cd; border-radius: 4px;">
                    <p style="color: #856404; margin: 0;">请点击下方按钮审核该商家的注册申请</p>
                </div>
                """,
                merchant.getBusinessName(),
                merchant.getContactPerson(),
                merchant.getPhone(),
                merchant.getEmail(),
                merchant.getBusinessLicense(),
                merchant.getBusinessAddress()
            );
            
            emailUtil.sendAuditEmail(
                CommonConstants.System.ADMIN_EMAIL,
                "新商家注册待审核 - " + merchant.getBusinessName(),
                content,
                auditUrl
            );
            
            log.info("商家注册通知邮件发送成功: {}", merchant.getBusinessName());
        } catch (Exception e) {
            log.error("商家注册通知邮件发送失败: {}", merchant.getBusinessName(), e);
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
    private void sendAdminRegisterNotification(AdminUser admin) {
        try {
            String auditUrl = String.format("%s/admin/audit/admin/%d?token=%s", 
                baseUrl, 
                admin.getId(), 
                generateAuditToken(admin.getId())
            );
            
            String content = String.format("""
                <h3 style="color: #1a73e8;">新管理员注册通知</h3>
                <div style="margin: 20px 0;">
                    <p><strong>用户名：</strong>%s</p>
                    <p><strong>昵称：</strong>%s</p>
                    <p><strong>邮箱：</strong>%s</p>
                    <p><strong>手机号：</strong>%s</p>
                    <p><strong>部门：</strong>%s</p>
                    <p><strong>职位：</strong>%s</p>
                </div>
                <div style="padding: 15px; background-color: #fff3cd; border-radius: 4px;">
                    <p style="color: #856404; margin: 0;">请点击下方按钮审核该管理员的注册申请</p>
                </div>
                """,
                admin.getUsername(),
                admin.getNickname(),
                admin.getEmail(),
                admin.getPhone(),
                admin.getDepartment(),
                admin.getPosition()
            );
            
            emailUtil.sendAuditEmail(
                CommonConstants.System.ADMIN_EMAIL,
                "新管理员注册待审核 - " + admin.getUsername(),
                content,
                auditUrl
            );
            
            log.info("管理员注册通知邮件发送成功: {}", admin.getUsername());
        } catch (Exception e) {
            log.error("管理员注册通知邮件发送失败: {}", admin.getUsername(), e);
        }
    }

    /**
     * 将token存储到Redis中
     * @param token JWT token
     * @param userId 用户ID
     */
    private void storeTokenInRedis(String token, Long userId) {
        // 使用userId作为key的一部分，便于按用户查看
        String userTokenKey = String.format("token:user:%d", userId);
        String tokenKey = String.format("token:auth:%s", token);
        
        // token基本信息
        Map<String, Object> tokenInfo = new HashMap<>();
        tokenInfo.put("userId", userId);
        tokenInfo.put("token", token);
        tokenInfo.put("createTime", LocalDateTime.now().toString());
        tokenInfo.put("expireTime", LocalDateTime.now().plusDays(2).toString());
        tokenInfo.put("lastAccessTime", LocalDateTime.now().toString());
        
        // 存储token信息，使用两个key便于查询
        redisTemplate.opsForValue().set(userTokenKey, tokenInfo, 2, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(tokenKey, tokenInfo, 2, TimeUnit.DAYS);
        
        log.info("Token已存储到Redis: userKey={}, tokenKey={}, userId={}", userTokenKey, tokenKey, userId);
    }

    /**
     * 生成审核token
     */
    private String generateAuditToken(Long id) {
        // 生成24小时有效的审核token
        String token = jwtUtil.generateAuditToken(id.toString());
        String key = "audit:token:" + id;
        redisTemplate.opsForValue().set(key, token, 24, TimeUnit.HOURS);
        return token;
    }
} 