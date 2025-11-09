package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.Controller.auth.ForgotPasswordController;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);


    /**
     * Gửi email reset password
     */
    public void sendResetPasswordEmail(String toEmail, String username, String resetUrl) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Đặt lại mật khẩu - DecorStore");

            String htmlContent = buildResetPasswordEmailTemplate(username, resetUrl);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Reset password email sent successfully to: {}", toEmail);

        } catch (MessagingException e) {
            log.error("Error sending reset password email to: {}", toEmail, e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Template email reset password
     */
    private String buildResetPasswordEmailTemplate(String username, String resetUrl) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta charset='UTF-8'>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }" +
                ".content { background-color: #f9f9f9; padding: 30px; border-radius: 0 0 5px 5px; }" +
                ".button { display: inline-block; padding: 12px 30px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; margin: 20px 0; }" +
                ".button:hover { background-color: #45a049; }" +
                ".footer { text-align: center; margin-top: 30px; color: #777; font-size: 12px; }" +
                ".warning { background-color: #fff3cd; border-left: 4px solid #ffc107; padding: 10px; margin: 15px 0; }" +
                ".link-box { background-color: #e9ecef; padding: 15px; border-radius: 5px; word-break: break-all; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h1 style='margin:0;'>🔐 DecorStore</h1>" +
                "<p style='margin:5px 0 0 0;'>Yêu cầu đặt lại mật khẩu</p>" +
                "</div>" +
                "<div class='content'>" +
                "<h2>Xin chào " + username + ",</h2>" +
                "<p>Chúng tôi nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.</p>" +
                "<p>Vui lòng nhấn vào nút bên dưới để đặt lại mật khẩu:</p>" +
                "<div style='text-align: center;'>" +
                "<a href='" + resetUrl + "' class='button'>🔑 Đặt lại mật khẩu</a>" +
                "</div>" +
                "<div class='warning'>" +
                "<strong>⚠️ Lưu ý quan trọng:</strong>" +
                "<ul style='margin: 10px 0;'>" +
                "<li>Link này sẽ hết hạn sau <strong>1 giờ</strong></li>" +
                "<li>Chỉ sử dụng được <strong>1 lần</strong></li>" +
                "<li>Không chia sẻ link này với bất kỳ ai</li>" +
                "</ul>" +
                "</div>" +
                "<p><strong>Nếu bạn không yêu cầu đặt lại mật khẩu,</strong> vui lòng bỏ qua email này. Tài khoản của bạn vẫn an toàn.</p>" +
                "<p style='margin-top: 20px;'><strong>Hoặc copy link sau vào trình duyệt:</strong></p>" +
                "<div class='link-box'>" +
                "<code style='color: #4CAF50; font-size: 13px;'>" + resetUrl + "</code>" +
                "</div>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>© 2024 DecorStore. All rights reserved.</p>" +
                "<p>Email này được gửi tự động, vui lòng không trả lời.</p>" +
                "<p style='color: #999; font-size: 11px; margin-top: 10px;'>Nếu bạn gặp vấn đề, vui lòng liên hệ support@decorstore.com</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Gửi email xác nhận đơn hàng (bonus - có thể dùng sau)
     */
    public void sendOrderConfirmationEmail(String toEmail, String orderCode, String orderDetails) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Xác nhận đơn hàng #" + orderCode + " - DecorStore");

            String htmlContent = buildOrderConfirmationTemplate(orderCode, orderDetails);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Order confirmation email sent to: {}", toEmail);

        } catch (MessagingException e) {
            log.error("Error sending order confirmation email", e);
        }
    }

    private String buildOrderConfirmationTemplate(String orderCode, String orderDetails) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head><meta charset='UTF-8'></head>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<div style='max-width: 600px; margin: 0 auto; padding: 20px;'>" +
                "<h2 style='color: #4CAF50;'> Xác nhận đơn hàng #" + orderCode + "</h2>" +
                "<p>Cảm ơn bạn đã đặt hàng tại DecorStore!</p>" +
                "<div style='background-color: #f9f9f9; padding: 20px; border-radius: 5px;'>" +
                orderDetails +
                "</div>" +
                "<p style='margin-top: 20px;'>Chúng tôi sẽ liên hệ với bạn sớm nhất để xác nhận đơn hàng.</p>" +
                "<p>Trân trọng,<br><strong>DecorStore Team</strong></p>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    /**
     * Gửi email thông báo đơn giản (plain text)
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false);

            mailSender.send(message);
            log.info("Simple email sent to: {}", to);

        } catch (MessagingException e) {
            log.error("Error sending simple email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
