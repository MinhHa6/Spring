package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "admin/login"; // trả về trang login.html
    }
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
        Customer customer = customerRepository
                .findByUsernameAndPasswordAndIsDeleteFalseAndIsActiveTrue(username, password);

        if (customer != null) {
            // lưu user vào session
            session.setAttribute("loggedInUser", customer);
            return "redirect:/admin"; // vào trang admin
        } else {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            return "login";
        }
    }

}
