package com.vuminhha.decorstore.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //  Dùng BCrypt mã hóa mật khẩu
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception
    {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf ->csrf.disable())// tam thoi disable csrf cho de test
                .authorizeHttpRequests(auth -> auth// cac url cong khai khong can dang nhap
                        .requestMatchers("/",
                                "/home","/shop/**","/product/**","/about","/contact",
                                "/blog/**",
                                "/login",
                                "/register",
                                "/assets/**",
                                "/uploads/**",
                                "/css/**",
                                "/js/**",
                                "/images/**").permitAll()
                // cho phep them vao gio hang (ca guest)
                        .requestMatchers("/cart","/cart/add").permitAll()
                // yeu cau dang nhap cho tat ca cac trang nay
                        .requestMatchers(
                                "/checkout/**",
                                "/order/**",
                                "/cart/update",
                                "/cart/remove/**",
                                "/profile/**"
                        ).authenticated()
                // Admin pages
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                // tat ca cac request khac can dang nhap
                                .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/login")// xu ly login
                        .loginProcessingUrl("/login")// url xu ly login
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(customSuccessHandler())// customer success handler
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).exceptionHandling(ex ->ex.accessDeniedPage("/access-denied"));
        return http.build();

    }
    @Bean
    public org.springframework.security.web.authentication.AuthenticationSuccessHandler customSuccessHandler() {
        return (request, response, authentication) -> {
            jakarta.servlet.http.HttpSession session = request.getSession();
            // Lấy URL redirect đã lưu
            String redirectUrl = (String) session.getAttribute("redirectAfterLogin");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                session.removeAttribute("redirectAfterLogin");
                response.sendRedirect(redirectUrl);
            } else {
                // Mặc định về trang chủ
                response.sendRedirect("/home");
            }
        };
    }
}
