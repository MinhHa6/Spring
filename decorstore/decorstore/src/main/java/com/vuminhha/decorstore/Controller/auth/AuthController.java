package com.vuminhha.decorstore.Controller.auth;

import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.repository.UserRepository;
import com.vuminhha.decorstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    public AuthController (UserService userService,UserRepository userRepository)
    {
        this.userService=userService;
        this.userRepository=userRepository;
    }


    // 👉 Hiển thị form đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register"; // sẽ trỏ tới file register.html trong templates/auth
    }

    // 👉 Xử lý đăng ký
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
        // check username đã tồn tại chưa
        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "❌ Username đã tồn tại!");
            return "auth/register";
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("error", "❌ Email đã tồn tại!");
            return "auth/register";
        }

        user.setActive(true);
        userRepository.save(user);
        model.addAttribute("success", "✅ Đăng ký thành công, mời đăng nhập!");
        return "redirect:/auth/login";
    }

    // 👉 Hiển thị form login
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/login"; // sẽ trỏ tới file login.html trong templates/auth
    }

    // 👉 Xử lý login
    @PostMapping("/login")
    public String processLogin(@ModelAttribute("user") User user, Model model) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
            model.addAttribute("message", "✅ Đăng nhập thành công!");
            model.addAttribute("username", existingUser.get().getUsername());
            return "redirect/admin/index"; // chuyển tới trang welcome
        } else {
            model.addAttribute("error", "❌ Sai username hoặc password!");
            return "auth/login";
        }
    }
}