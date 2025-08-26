package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public List<Customer> getAll ()
    {
        return customerRepository.findAll();
    }
    // lay khach hang theo di a chi id
    public Customer getCustomerById(Long id)
    {
        return customerRepository.findById(id).orElseThrow(()-> new RuntimeException("Customer null "));
    }
    // update -create
    public Customer saveCustomer (Customer customer)
    {
        return customerRepository.save(customer);
    }
    // xoa khach hang theo id
    public void deleteCustomer (Long id )
    {
        customerRepository.deleteById(id);
    }
}
