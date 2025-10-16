package com.vuminhha.decorstore.Controller.auth;

import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService)
    {
        this.userService=userService;
    }
    /**
     * Dang ky
     *
     */
    @PostMapping("/register")
    public ResponseEntity<?>register(@RequestParam String username,@RequestParam String email,
                                     @RequestParam String password)
    {
        try {
            User user = userService.register(username, email, password, null, null);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    /**
     * Đăng nhập
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username,
                                   @RequestParam String password) {
        try {
            User user = userService.login(username, password);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
