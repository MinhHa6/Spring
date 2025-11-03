package com.vuminhha.decorstore.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // ✅ Dùng BCrypt mã hóa mật khẩu
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // ✅ Các trang cho phép truy cập tự do
                        .requestMatchers("/", "/home", "/shop", "/about", "/blog", "/contact",
                                "/cart/**", "/product/**", "/images/**", "/css/**", "/js/**").permitAll()

                        // ✅ Các trang yêu cầu đăng nhập (admin, checkout, v.v.)
                        .requestMatchers("/admin","admin/**", "/checkout/**", "/account/**").authenticated()

                        // ✅ Mặc định tất cả phải xác thực
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .defaultSuccessUrl("/admin",true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/home?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Có thể bật lại nếu dùng form chuẩn

        return http.build();
    }
}
