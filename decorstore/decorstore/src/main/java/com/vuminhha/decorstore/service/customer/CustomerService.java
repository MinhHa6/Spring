package com.vuminhha.decorstore.service.customer;


import com.vuminhha.decorstore.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> getAll();
    Customer getCustomerById(Long id);
    void deleteCustomerById(Long id);
    List<Customer> searchCustomer(String keyword);
    Customer saveCustomer (Customer customer);
    Customer createCustomerProfile(Long userId, String name, String address, String phone, String avatar);
    Optional<Customer> getCustomerByUserId(Long userId);
    Customer updateCustomer(Long customerId, String name, String address, String phone, String avatar);
}
