package Lesson6_Datajpa.controller;

import Lesson6_Datajpa.dto.CustomerDTO;
import Lesson6_Datajpa.entity.Customer;
import Lesson6_Datajpa.service.CustomerService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CompletionService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    public CustomerController (CustomerService customerService)
    {
        this.customerService=customerService;
    }
    @GetMapping
    public String getCustomers(Model model)
    {
        model.addAttribute("customers",customerService.fillAll());
        return  "students/customer-list";
    }
    @GetMapping("/add")
    public String addNewCustomer(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "templates.students/customer-add";  // ✅ Trùng với file bạn đã tạo
    }


    @PostMapping
    public String saveCustomer(@ModelAttribute("customer")CustomerDTO customerDTO)
    {
        customerService.save(customerDTO);
        return "redirect:/customers";
    }
}
