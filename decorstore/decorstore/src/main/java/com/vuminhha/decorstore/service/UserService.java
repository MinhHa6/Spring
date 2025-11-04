package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.entity.Role;
import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.repository.RoleRepository;
import com.vuminhha.decorstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;// dung  BCryptPasswordEncoder
    public UserService (UserRepository userRepository,PasswordEncoder passwordEncoder,RoleRepository roleRepository)
    {
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.roleRepository=roleRepository;
    }
    /**
     * Dang ky tai khoan moi
     */
    @Transactional
    public User register(String username, String email, String rawPassword, String fullName) {
        // 1️⃣ Kiểm tra trùng username hoặc email
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // 2️⃣ Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 3️⃣ Lấy role mặc định (ROLE_USER)
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền mặc định ROLE_USER"));

        // 4️⃣ Tạo User (dùng setter để tránh lỗi constructor)
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRoles(Set.of(defaultRole));
        user.setActive(true);

        // 5️⃣ Tạo Customer liên kết 1-1 với User
        Customer customer = new Customer();
        customer.setName(fullName);
        customer.setActive(true);
        customer.setDelete(false);
        customer.setUser(user); // liên kết ngược
        user.setCustomer(customer); // liên kết xuôi

        // 6️⃣ Lưu user (JPA sẽ tự cascade lưu customer nếu đã bật cascade)
        return userRepository.save(user);
    }

    /**
     * Dang nhap
     */
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
    public boolean hasRole(User user, String roleName) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName().equals(roleName));
    }

    /**
     * 🔍 Tìm user theo ID
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    /**
     * Tìm user theo username (trả về User trực tiếp)
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}
