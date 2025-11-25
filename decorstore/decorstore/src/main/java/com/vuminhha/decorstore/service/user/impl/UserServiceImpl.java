package com.vuminhha.decorstore.service.user.impl;

import com.vuminhha.decorstore.config.exception.ResourceNotFoundException;
import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.entity.Role;
import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.repository.RoleRepository;
import com.vuminhha.decorstore.repository.UserRepository;
import com.vuminhha.decorstore.service.user.UserService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Service
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserServiceImpl implements UserService {
    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;// dung  BCryptPasswordEncoder
    /**
     * Dang ky tai khoan moi
     */
    @Transactional
    @Override
    public User register(String username, String email, String rawPassword, String fullName) {
        // Kiểm tra trùng username hoặc email
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        //  Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(rawPassword);

        //  Lấy role mặc định (ROLE_USER)
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền mặc định ROLE_USER"));

        //  Tạo User (dùng setter để tránh lỗi constructor)
        User user = User.builder()
                .username(username)
                .email(email)
                .password(encodedPassword)
                .roles(Set.of(defaultRole))
                .isActive(true)
                .customer(customer) // liên kết xuôi
                .build();

        //  Tạo Customer liên kết 1-1 với User
        Customer customer = new Customer();
        customer.setName(fullName);
        customer.setActive(true);
        customer.setDelete(false);
        customer.setUser(user); // liên kết ngược
        user.setCustomer(customer); // liên kết xuôi

        // ⃣Lưu user (JPA sẽ tự cascade lưu customer nếu đã bật cascade)
        return userRepository.save(user);
    }

    /**
     * Dang nhap
     */
    @Override
    public User login (String username,String rawPassword)
    {
        Optional<User> optionalUser= userRepository.findByUsername(username);
        if(optionalUser.isEmpty())
        {
            throw  new RuntimeException("User not found");
        }
        User user= optionalUser.get();
        // so khop mat khau
        if(!passwordEncoder.matches(rawPassword,user.getPassword()))
        {
            throw  new RuntimeException("Invalid password");
        }
        if(!user.getActive())
        {
            throw new RuntimeException("Account is inactive");
        }
        return user;
    }
    /**
     * Kiem tra phan quyen( vi du Admin)
     */
    @Override
    public boolean hasRole(User user, String roleName) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equals(roleName));
    }

    /**
     * Tìm user theo ID
     */
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    /**
     * Tìm user theo username (trả về User trực tiếp)
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }
    /**
     * Cập nhật mật khẩu
     */
    @Override
    @Transactional
    public void updatePassword(User user) {
        userRepository.save(user);
    }
    /**
     * tim user theo email
     */
    @Override
    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Email not found:"+email));
    }
    @Override
    public User findByResetToken(String resetToken)
    {
        return userRepository.findByResetToken(resetToken).orElseThrow(()-> new ResourceNotFoundException("Reset token not found"));
    }
    @Override
    public  void saveUser(User user)
    {
        userRepository.save(user);
    }

}
