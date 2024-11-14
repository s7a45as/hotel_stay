package com.homestay.common.utils;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
@Component
public class EmailUtil {

    private String emailHost = "smtp.163.com";       // 发送邮件的主机
    private String transportType = "smtp";           // 邮件发送的协议
    private String fromUser = "民俗预订官网";           // 发件人名称
    private String fromEmail = "18589826376@163.com";  // 发件人邮箱
    private String authCode = "ATRHLQgpRYTBbXsu";    // 发件人邮箱授权码

    public void sendVerificationCode(String recipientEmail, String verificationCode,String expirationTime ) throws UnsupportedEncodingException, MessagingException {
        // 初始化默认参数
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", transportType);
        props.setProperty("mail.host", emailHost);
        props.setProperty("mail.user", fromUser);
        props.setProperty("mail.from", fromEmail);

        // 获取Session对象
        Session session = Session.getInstance(props, null);
        session.setDebug(true); // 开启调试信息

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
        message.setSubject("验证码");

        // 设置邮件内容，使用HTML格式
        String content = "<h1>您的验证码是：" + verificationCode + "</h1><p>请在"+expirationTime+"内使用。</p>";
        message.setContent(content, "text/html;charset=UTF-8");

        // 保存邮件内容
        message.saveChanges();

        // 发送邮件
        try (Transport transport = session.getTransport()) {
            transport.connect(fromEmail, authCode); // 使用邮箱和授权码连接
            transport.sendMessage(message, message.getAllRecipients()); // 发送
        }
    }
}
