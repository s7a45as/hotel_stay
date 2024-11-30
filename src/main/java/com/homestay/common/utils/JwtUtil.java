package com.homestay.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret:your-256-bit-secret-key-must-be-at-least-32-bytes-long-for-security}")
    private String secret;

    @Value("${jwt.expiration:86400}")
    private Long expiration;

    /**
     * 获取用于签名的密钥
     *
     * 此方法用于生成一个固定长度的密钥，以便在签名算法中使用
     * 它首先将秘密字符串转换为字节数组，然后确保这个字节数组的长度不超过32字节
     * 如果秘密字符串的长度小于32字节，它将直接使用这些字节；如果超过32字节，它只使用前32字节
     * 最后，它使用这些字节创建并返回一个SecretKey对象，用于HMAC SHA签名算法
     *
     * @return SecretKey 用于签名的密钥
     */
    private SecretKey getSigningKey() {
        // 将秘密字符串转换为UTF-8编码的字节数组
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

        // 创建一个32字节长度的字节数组，用于存储有效的密钥字节
        byte[] validKeyBytes = new byte[32];

        // 复制密钥字节到新的字节数组中，最多复制32字节
        System.arraycopy(keyBytes, 0, validKeyBytes, 0, Math.min(keyBytes.length, 32));

        // 使用有效的密钥字节创建并返回一个SecretKey对象
        return Keys.hmacShaKeyFor(validKeyBytes);
    }

    /**
     * 生成token (用于登录)
     */
    /**
     * 生成JWT令牌
     *
     * @param userId 用户ID，用于标识令牌的所有者
     * @param role 用户角色，暂未在令牌生成中使用，可预留用于后续扩展
     * @return 生成的JWT令牌字符串
     */
    public String generateToken(Long userId, String role) {
        // 获取当前时间
        Date now = new Date();
        // 计算令牌过期时间
        Date expiryDate = new Date(now.getTime() + expiration * 1000);
        String subject = userId.toString()+ role;
        // 构建并返回JWT令牌
        return Jwts.builder()
                .setSubject(subject) // 设置令牌主题为用户ID
                .setIssuedAt(now) // 设置令牌发行时间为当前时间
                .setExpiration(expiryDate) // 设置令牌过期时间
                .signWith(getSigningKey()) // 使用签名密钥对令牌进行签名
                .compact(); // 生成紧凑的JWT字符串
    }

    /**
     * 生成审核token
     */
    public String generateAuditToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 24 * 60 * 60 * 1000); // 24小时过期

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 从token中获取用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return Long.valueOf(claims.getSubject());
    }

    /**
     * 从token中获取主题
     */
    public String getSubjectFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * 验证token是否过期
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证token是否有效
     */
    public boolean validateToken(String token) {
        try {
            getAllClaimsFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中获取所有的claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
} 