package Lesson6_Datajpa.service;

import Lesson6_Datajpa.dto.CustomerDTO;
import Lesson6_Datajpa.entity.Customer;
import Lesson6_Datajpa.repository.CustomerRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;
    @Autowired
    public CustomerService (CustomerRepository customerRepository)
    {
        this.customerRepository=customerRepository;
    }

    // lay thong tin theo dia chi Id
    public List<CustomerDTO> fillAll() {
        return customerRepository.findAll()
                .stream().map(customer -> {
                    CustomerDTO customerDTO = new CustomerDTO();
                    customerDTO.setId(customer.getId());
                    customerDTO.setUsername(customer.getUsername());
                    customerDTO.setPassword(customer.getPassword());  // ✅ sửa
                    customerDTO.setFullName(customer.getFullName());
                    customerDTO.setAddress(customer.getAddress());    // ✅ sửa
                    customerDTO.setPhone(customer.getPhone());
                    customerDTO.setEmail(customer.getEmail());
                    customerDTO.setBirthDay(customer.getBirthDay());
                    customerDTO.setActive(customer.getActive());

                    return customerDTO;
                }).toList();
    }
    //Luu thong tin
    public Boolean save(CustomerDTO customerDTO)
    {
        Customer customer= new Customer();
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setFullName(customerDTO.getFullName());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setBirthDay(customerDTO.getBirthDay());
        customer.setActive(customerDTO.getActive());
        try
        {
            customerRepository.save(customer);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    //Update
    // Update Customer by ID
    public Customer updateCustomerById(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setUsername(customerDTO.getUsername());
                    customer.setPassword(customerDTO.getPassword());
                    customer.setFullName(customerDTO.getFullName());
                    customer.setAddress(customerDTO.getAddress()); // Nếu có sai chính tả thì nên đổi thành setAddress
                    customer.setPhone(customerDTO.getPhone());
                    customer.setEmail(customerDTO.getEmail());
                    customer.setBirthDay(customerDTO.getBirthDay()); // Đã sửa thiếu dấu ;
                    customer.setActive(customerDTO.getActive());
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + id));
    }
    public void deleteCustomer(Long id)
    {
        customerRepository.deleteById(id);
    }

}
