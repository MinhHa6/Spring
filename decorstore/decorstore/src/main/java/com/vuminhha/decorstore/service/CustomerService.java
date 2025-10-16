package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.repository.CustomerRepository;
import com.vuminhha.decorstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    public CustomerService(UserRepository userRepository,CustomerRepository customerRepository)
    {
        this.userRepository=userRepository;
        this.customerRepository=customerRepository;
    }

    /**
     * Tạo Customer profile cho User
     */
    public Customer createCustomerProfile(Long userId, String name, String address, String phone, String avatar) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getCustomer() != null) {
            throw new RuntimeException("Customer profile already exists for this user");
        }

        Customer customer = new Customer();
              customer.setName(name);
                customer.setAddress(address);
                customer.setPhone(phone);
                customer.setAvatar(avatar);
                customer.setUser(user);
                customer.setActive(true);
                customer.setDelete(false);

        return customerRepository.save(customer);
    }

    /**
     * 🔍 Lấy thông tin Customer theo User ID
     */
    public Optional<Customer> getCustomerByUserId(Long userId) {
        return customerRepository.findByUserId(userId);
    }

    /**
     * Cập nhật thông tin profile
     */
    public Customer updateCustomer(Long customerId, String name, String address, String phone, String avatar) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setName(name);
        customer.setAddress(address);
        customer.setPhone(phone);
        customer.setAvatar(avatar);

        return customerRepository.save(customer);
    }
}
