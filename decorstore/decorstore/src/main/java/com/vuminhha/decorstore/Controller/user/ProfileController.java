package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.service.customer.CustomerService;
import com.vuminhha.decorstore.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import static com.vuminhha.decorstore.constants.ProfileConstants.*;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProfileController {
     UserService userService;
     CustomerService customerService;
     PasswordEncoder passwordEncoder;
     static Logger log = LoggerFactory.getLogger(ProfileController.class);

    /**
     * Hiển thị trang profile
     */
    @GetMapping
    public String showProfile(Model model, Principal principal) {
        if (principal == null) {
            return REDIRECT_LOGIN;
        }

        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);

            // Lấy thông tin customer (nếu có)
            Optional<Customer> customerOpt = customerService.getCustomerByUserId(user.getId());

            model.addAttribute(USER_ATTRIBUTE, user);
            model.addAttribute(CUSTOMER_ATTRIBUTE, customerOpt.orElse(null));

            return PROFILE_VIEW;

        } catch (Exception e) {
            log.error("Error loading profile: ", e);
            return REDIRECT_HOME_PROFILE_ERROR;
        }
    }

    /**
     * Cập nhật thông tin profile
     */
    @PostMapping("/update")
    public String updateProfile(@RequestParam String fullName,
                                @RequestParam(required = false) String phone,
                                @RequestParam(required = false) String address,
                                @RequestParam(required = false) MultipartFile avatarFile,
                                Principal principal,
                                Model model) {
        if (principal == null) {
            return REDIRECT_LOGIN;
        }

        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);

            Optional<Customer> customerOpt = customerService.getCustomerByUserId(user.getId());

            String avatarPath = null;

            // Xử lý upload avatar
            if (avatarFile != null && !avatarFile.isEmpty()) {
                avatarPath = saveFile(avatarFile);
            }

            if (customerOpt.isPresent()) {
                // Cập nhật customer hiện có
                Customer customer = customerOpt.get();
                customerService.updateCustomer(
                        customer.getId(),
                        fullName,
                        address,
                        phone,
                        avatarPath != null ? avatarPath : customer.getAvatar()
                );
            } else {
                // Tạo customer mới
                customerService.createCustomerProfile(
                        user.getId(),
                        fullName,
                        address,
                        phone,
                        avatarPath
                );
            }

            log.info("Profile updated successfully for user: {}", username);
            return REDIRECT_PROFILE_SUCCESS;

        } catch (Exception e) {
            log.error("Error updating profile: ", e);
            model.addAttribute(ERROR_ATTRIBUTE, "Có lỗi xảy ra khi cập nhật thông tin!");
            return showProfile(model, principal);
        }
    }

    /**
     * Đổi mật khẩu
     */
    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Principal principal,
                                 Model model) {
        if (principal == null) {
            return REDIRECT_LOGIN;
        }

        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);

            // Kiểm tra mật khẩu hiện tại
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                model.addAttribute(PASSWORD_ERROR, "Mật khẩu hiện tại không đúng!");
                return showProfile(model, principal);
            }

            // Kiểm tra mật khẩu mới khớp
            if (!newPassword.equals(confirmPassword)) {
                model.addAttribute(PASSWORD_ERROR, "Mật khẩu xác nhận không khớp!");
                return showProfile(model, principal);
            }

            // Cập nhật mật khẩu
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.updatePassword(user);

            log.info("Password changed successfully for user: {}", username);
            return REDIRECT_PROFILE_PASSWORD_CHANGED;

        } catch (Exception e) {
            log.error("Error changing password: ", e);
            model.addAttribute(PASSWORD_ERROR, "Có lỗi xảy ra khi đổi mật khẩu!");
            return showProfile(model, principal);
        }
    }

    /**
     * Lưu file upload
     */
    private String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // Tạo tên file unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;

        // Tạo thư mục nếu chưa tồn tại
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Lưu file
        Path path = Paths.get(UPLOAD_DIR + newFilename);
        Files.write(path, file.getBytes());

        return newFilename;
    }
}