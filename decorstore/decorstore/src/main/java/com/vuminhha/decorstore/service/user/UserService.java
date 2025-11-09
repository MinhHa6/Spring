package com.vuminhha.decorstore.service.user;

import com.vuminhha.decorstore.entity.User;

import java.util.Optional;

public interface UserService {
    User register(String username, String email, String rawPassword, String fullName);
    User login (String username,String rawPassword);
    boolean hasRole(User user, String roleName);
    Optional<User> getUserById(Long id);
    User findByUsername(String username);
    void updatePassword(User user);
    User findByEmail(String email);

    /**
     * Tìm user theo reset token
     */
    User findByResetToken(String resetToken);

    /**
     * Lưu/cập nhật user
     */
    void saveUser(User user);
}
