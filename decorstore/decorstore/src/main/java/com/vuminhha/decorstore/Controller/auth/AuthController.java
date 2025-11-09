package com.vuminhha.decorstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

//    private final UserService userService;
//    public AuthController(UserService userService)
//    {
//        this.userService=userService;
//    }
//
//    // Hiển thị form login (Spring Security sẽ handle POST /login)
//    @GetMapping("/login")
//    public String loginPage() {
//        return "auth/login"; // => src/main/resources/templates/auth/login.html
//    }
//
//    // Hiển thị form đăng ký
//    @GetMapping("/register")
//    public String registerPage(Model model) {
//        model.addAttribute("user", new User());
//        return "auth/register"; // => src/main/resources/templates/auth/register.html
//    }
//
//    // Xử lý đăng ký
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute("user") User user) {
//        // ở đây bạn chỉ nhận username, email, password
//        userService.register(user.getUsername(), user.getEmail(), user.getPassword(), null, null);
//        return "redirect:/auth/login?registered"; // đăng ký thành công => chuyển qua login
//    }
//
//    // Trang home sau khi login
//    @GetMapping("/admin/index")
//    public String homePage() {
//        return "admin/index"; // => src/main/resources/templates/auth/home.html
//    }
}
