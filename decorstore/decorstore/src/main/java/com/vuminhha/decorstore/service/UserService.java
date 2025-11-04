package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Role;
import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.repository.RoleRepository;
import com.vuminhha.decorstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public User register(String username, String email, String rawPassword, Role role,String fullName)
    {
        // check username hoac email da ton tai chua
        if(userRepository.existsByUsername(username))
        {
            throw new RuntimeException("Username already exists ");
        }
        if(userRepository.existsByEmail(email))
        {
            throw  new RuntimeException("Email already exists");
        }
        // Ma hoa mat khau
        String encodePassword= passwordEncoder.encode(rawPassword);
        // Gán role mặc định (ROLE_USER)
            Role defaultRole = roleRepository.findByName("ROLE_USER")
            .orElseThrow(() -> new RuntimeException("Default role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(defaultRole);
        // tao user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodePassword);
        user.setRoles(roles);
        user.setActive(true);
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
