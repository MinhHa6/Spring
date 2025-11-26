package com.vuminhha.decorstore.service.customer;

import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.repository.CustomerRepository;
import com.vuminhha.decorstore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    public CustomerServiceImpl(UserRepository userRepository,CustomerRepository customerRepository)
    {
        this.userRepository=userRepository;
        this.customerRepository=customerRepository;
    }
    /**
     * Admin lay ra tat ca nguoi dung
     */
    @Override
    public List<Customer> getAll()
    {
        return customerRepository.findAll();
    }
    /**
     * Lay nguoi dung theo dia chi id
     */
    @Override
    public Customer getCustomerById(Long id)
    {
        return customerRepository.findById(id).orElseThrow(()-> new RuntimeException("customer not found by id:"+id));
    }
    /**
     * xoa customer theo id
     */
    @Override
    public void deleteCustomerById(Long id)
    {
        customerRepository.deleteById(id);
    }
    @Override
    public List<Customer> searchCustomer(String keyword)
    {
        return customerRepository.searchCustomers(keyword);
    }
    /**
     * Them va update customer
     */
    @Override
    public Customer saveCustomer (Customer customer)
    {
        return customerRepository.save(customer);
    }
    /**
     * Tạo Customer profile cho User
     */
    @Override
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
        customer.setIsActive(true);
        customer.setIsDelete(false);

        return customerRepository.save(customer);
    }

    /**
     *  Lấy thông tin Customer theo User ID
     */
    @Override
    public Optional<Customer> getCustomerByUserId(Long userId) {
        return customerRepository.findByUserId(userId);
    }

    /**
     * Cập nhật thông tin profile
     */
    @Override
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
