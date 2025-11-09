package com.vuminhha.decorstore.Controller.auth;

import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    @Autowired
    private  UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String processRegister(
            @ModelAttribute("user") User user,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("fullName") String fullName, // Thêm input fullName từ form
            Model model) {

        String password= user.getPassword();
        // regex mat khau manh
        String regex="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        try {
            if(!password.matches(regex))
            {
                model.addAttribute("error","Mat khau chua dung dinh dang!");
                return "users/register";
            }
            // 1 Kiểm tra mật khẩu nhập lại
            if (!user.getPassword().equals(confirmPassword)) {
                model.addAttribute("error", "Mật khẩu nhập lại không khớp!");
                return "users/register";
            }

            //  Kiểm tra dữ liệu bắt buộc
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                model.addAttribute("error", "Tên đăng nhập không được để trống!");
                return "users/register";
            }
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                model.addAttribute("error", "Email không được để trống!");
                return "users/register";
            }
            if (fullName == null || fullName.trim().isEmpty()) {
                model.addAttribute("error", "Họ và tên không được để trống!");
                return "users/register";
            }

            // Gọi service để xử lý đăng ký (User + Customer)
            userService.register(
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    fullName
            );
            //  Thông báo thành công
            model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            model.addAttribute("user", new User()); // Reset form
            return "users/register";

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "users/register";
        }
    }


}
