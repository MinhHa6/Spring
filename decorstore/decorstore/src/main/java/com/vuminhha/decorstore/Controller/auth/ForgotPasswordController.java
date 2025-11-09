package com.vuminhha.decorstore.Controller.auth;

import com.vuminhha.decorstore.Controller.user.OrderUserController;
import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.repository.UserRepository;
import com.vuminhha.decorstore.service.EmailService;
import com.vuminhha.decorstore.service.cart.impl.CartServiceImpl;
import com.vuminhha.decorstore.service.user.UserService;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@Slf4j
public class ForgotPasswordController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(ForgotPasswordController.class);


    @GetMapping("/test-email")
    public String testEmail() {
        try {
            emailService.sendSimpleEmail(
                    "test@gmail.com",
                    "Test Email",
                    "This is a test email"
            );
            return "Email sent!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    /**
     * Hiển thị trang quên mật khẩu
     */
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "users/forgot-password";
    }

    /**
     * Xử lý gửi email reset password
     */
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, Model model) {
        try {
            User user = userService.findByEmail(email);

            if (user == null) {
                model.addAttribute("error", "Email không tồn tại trong hệ thống!");
                return "users/forgot-password";
            }

            // Tạo token reset password
            String resetToken = UUID.randomUUID().toString();
            user.setResetToken(resetToken);
            user.setResetTokenExpiry(LocalDateTime.now().plusHours(1)); // Token hết hạn sau 1 giờ
            userService.saveUser(user);

            // Gửi email
            String resetUrl = "http://localhost:8080/reset-password?token=" + resetToken;
            emailService.sendResetPasswordEmail(user.getEmail(), user.getUsername(), resetUrl);

            log.info("Password reset email sent to: {}", email);
            model.addAttribute("success", "Email hướng dẫn đặt lại mật khẩu đã được gửi đến " + email);
            return "users/forgot-password";

        } catch (Exception e) {
            log.error("Error processing forgot password: ", e);
            model.addAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại!");
            return "users/forgot-password";
        }
    }

    /**
     * Hiển thị trang reset password
     */
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        try {
            User user = userService.findByResetToken(token);

            if (user == null) {
                model.addAttribute("error", "Link đặt lại mật khẩu không hợp lệ!");
                return "users/reset-password";
            }

            // Kiểm tra token đã hết hạn chưa
            if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
                model.addAttribute("error", "Link đặt lại mật khẩu đã hết hạn!");
                return "users/reset-password";
            }

            model.addAttribute("token", token);
            return "users/reset-password";

        } catch (Exception e) {
            log.error("Error showing reset password form: ", e);
            model.addAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại!");
            return "users/reset-password";
        }
    }

    /**
     * Xử lý đặt lại mật khẩu
     */
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmPassword,
                                       Model model) {
        try {
            // Kiểm tra mật khẩu khớp
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
                model.addAttribute("token", token);
                return "users/reset-password";
            }

            // Kiểm tra độ dài mật khẩu
            if (newPassword.length() < 6) {
                model.addAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự!");
                model.addAttribute("token", token);
                return "users/reset-password";
            }

            User user = userService.findByResetToken(token);

            if (user == null) {
                model.addAttribute("error", "Link đặt lại mật khẩu không hợp lệ!");
                return "users/reset-password";
            }

            // Kiểm tra token hết hạn
            if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
                model.addAttribute("error", "Link đặt lại mật khẩu đã hết hạn!");
                return "users/reset-password";
            }

            // Cập nhật mật khẩu mới
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            user.setResetTokenExpiry(null);
            userService.saveUser(user);

            log.info("Password reset successfully for user: {}", user.getUsername());
            model.addAttribute("success", "Đặt lại mật khẩu thành công! Bạn có thể đăng nhập với mật khẩu mới.");
            return "users/reset-password";

        } catch (Exception e) {
            log.error("Error processing reset password: ", e);
            model.addAttribute("error", "Có lỗi xảy ra. Vui lòng thử lại!");
            model.addAttribute("token", token);
            return "users/reset-password";
        }
    }
}
