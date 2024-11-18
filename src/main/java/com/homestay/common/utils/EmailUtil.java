package com.homestay.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailUtil {

    @Value("${spring.mail.host:smtp.163.com}")
    private String emailHost;       // 发送邮件的主机
    
    @Value("${spring.mail.protocol:smtp}")
    private String transportType;   // 邮件发送的协议
    
    @Value("${spring.mail.username:18589826376@163.com}")
    private String fromEmail;       // 发件人邮箱
    
    @Value("${spring.mail.password:ATRHLQgpRYTBbXsu}")
    private String authCode;        // 发件人邮箱授权码
    
    @Value("${spring.mail.properties.mail.from.name:民俗预订官网}")
    private String fromUser;        // 发件人名称

    /**
     * 发送验证码邮件
     */
    public void sendVerificationCode(String recipientEmail, String verificationCode, String expirationTime) 
            throws MessagingException, UnsupportedEncodingException {
        String subject = "验证码";
        String content = String.format("""
            <div style="padding: 20px; background-color: #f8f9fa; border-radius: 5px;">
                <h2 style="color: #1a73e8;">验证码</h2>
                <div style="margin: 20px 0; padding: 20px; background-color: #ffffff; border-radius: 4px; text-align: center;">
                    <h1 style="color: #333333; font-size: 24px; margin: 0;">%s</h1>
                </div>
                <p style="color: #666666;">请在 <strong>%s</strong> 内使用该验证码。</p>
                <p style="color: #999999; font-size: 12px; margin-top: 20px;">
                    如果这不是您的操作，请忽略此邮件。
                </p>
            </div>
            """, 
            verificationCode, 
            expirationTime
        );
        
        sendEmail(recipientEmail, subject, content);
    }

    /**
     * 发送审核链接邮件
     */
    public void sendAuditEmail(String recipientEmail, String title, String content, String auditUrl) 
            throws MessagingException, UnsupportedEncodingException {
        String htmlContent = String.format("""
            <div style="padding: 20px; background-color: #f8f9fa; border-radius: 5px;">
                %s
                <div style="margin-top: 20px; text-align: center;">
                    <div style="margin: 10px 0;">
                        <a href="%s&approved=true" style="display: inline-block; padding: 10px 20px; 
                            background-color: #28a745; color: #ffffff; text-decoration: none; 
                            border-radius: 5px; margin: 0 10px;">
                            通过审核
                        </a>
                        <a href="%s&approved=false" style="display: inline-block; padding: 10px 20px; 
                            background-color: #dc3545; color: #ffffff; text-decoration: none; 
                            border-radius: 5px; margin: 0 10px;">
                            拒绝审核
                        </a>
                    </div>
                    <div style="margin-top: 10px; font-size: 12px; color: #6c757d;">
                        如果按钮无法点击，请复制以下链接到浏览器访问：<br>
                        <span style="color: #007bff;">%s</span>
                    </div>
                </div>
                <p style="color: #999999; font-size: 12px; margin-top: 20px; text-align: center;">
                    此链接有效期为24小时，请及时处理。
                </p>
            </div>
            """, 
            content,
            auditUrl,  // 通过审核链接
            auditUrl,  // 拒绝审核链接
            auditUrl   // 原始链接
        );
        
        sendEmail(recipientEmail, title, htmlContent);
    }

    /**
     * 发送通用邮件
     */
    private void sendEmail(String recipientEmail, String subject, String htmlContent) 
            throws MessagingException, UnsupportedEncodingException {
        // 初始化默认参数
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", transportType);
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.user", fromUser);
        props.setProperty("mail.from", fromEmail);
        
        // 开启SSL加密
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.auth", "true");

        // 获取Session对象
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, authCode);
            }
        });
        session.setDebug(true); // 开启调试信息

        try {
            // 创建MimeMessage
            MimeMessage message = new MimeMessage(session);
            
            // 设置发件人
            String formName = MimeUtility.encodeWord(fromUser) + " <" + fromEmail + ">";
            InternetAddress from = new InternetAddress(formName);
            message.setFrom(from);

            // 设置收件人
            InternetAddress to = new InternetAddress(recipientEmail);
            message.setRecipient(Message.RecipientType.TO, to);

            // 设置邮件主题
            message.setSubject(subject, "UTF-8");

            // 设置邮件内容
            message.setContent(htmlContent, "text/html;charset=UTF-8");

            // 保存邮件内容
            message.saveChanges();

            // 发送邮件
            Transport transport = session.getTransport();
            transport.connect(fromEmail, authCode);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
            log.info("邮件发送成功: {}", recipientEmail);
        } catch (Exception e) {
            log.error("邮件发送失败: {}", recipientEmail, e);
            throw e;
        }
    }
}
