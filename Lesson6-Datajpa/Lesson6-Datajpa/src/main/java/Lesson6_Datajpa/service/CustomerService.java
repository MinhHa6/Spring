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
    //ListAll
    public List<CustomerDTO>fillAll()
    {
        return customerRepository.findAll()
                .stream().map(customer -> {
                    CustomerDTO customerDTO= new CustomerDTO();
                    customerDTO.setId(customer.getId());
                    customerDTO.setUserName(customer.getUserName());
                    customerDTO.setPassWord(customer.getPassWord());
                    customerDTO.setFullName(customer.getFullName());
                    customerDTO.setAdress(customer.getAdress());
                    customerDTO.setPhone(customer.getPhone());
                    customerDTO.setEmail(customer.getEmail());
                    customerDTO.setBirthDay(customer.getBirthDay())
                    customerDTO.setActive(customer.getActive());

                    return customerDTO;
                }).toList();
    }
    // lay thong tin theo dia chi Id
    public Optional<CustomerDTO>findById(Long id)
    {
        Customer customer= customerRepository.findById(id).orElse(null);
        CustomerDTO customerDTO=new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setUserName(customer.getUserName());
        customerDTO.setPassWord(customer.getPassWord());
        customerDTO.setFullName(customer.getFullName());
        customerDTO.setAdress(customer.getAdress());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setBirthDay(customer.getBirthDay())
        customerDTO.setActive(customer.getActive());
        return Optional.of(customerDTO);

    }
    //Luu thong tin
    public Boolean save(CustomerDTO customerDTO)
    {
        Customer customer= new Customer();
        customer.setUserName(customerDTO.getUserName());
        customer.setPassWord(customerDTO.getPassWord());
        customer.setFullName(customerDTO.getFullName());
        customer.setAdress(customerDTO.getAdress());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setBirthDay(customerDTO.getBirthDay())
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
                    customer.setUserName(customerDTO.getUserName());
                    customer.setPassWord(customerDTO.getPassWord());
                    customer.setFullName(customerDTO.getFullName());
                    customer.setAdress(customerDTO.getAdress()); // Nếu có sai chính tả thì nên đổi thành setAddress
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
