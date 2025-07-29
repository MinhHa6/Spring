package Lesson6_Datajpa.controller;

import Lesson6_Datajpa.dto.CustomerDTO;
import Lesson6_Datajpa.entity.Customer;
import Lesson6_Datajpa.service.CustomerService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        return "students/customer-add";  // ✅ Trùng với file bạn đã tạo
    }

    @PostMapping
    public String saveCustomer(@ModelAttribute("customer")CustomerDTO customerDTO)
    {
        customerService.save(customerDTO);
        return "redirect:/customers";
    }
    // update
    @GetMapping("/edit/{id}")
    public String showCustomer(@PathVariable(value = "id")Long id,Model model)
    {
        CustomerDTO customerDTO= customerService.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid customer Id:"+id));
        model.addAttribute("customer",customerDTO);
        return "students/customer-edit";
    }
  @PostMapping("/update/{id}")
  public String updateCustomer(@PathVariable(value = "id")Long id,@ModelAttribute("customer")CustomerDTO customerDTO)
  {
      customerService.updateCustomerById(id,customerDTO);
      return "redirect:/customers";
  }

    // xoa
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable(value = "id")Long id)
    {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
