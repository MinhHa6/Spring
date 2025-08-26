package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping
    public String listAll(Model model) {
    model.addAttribute("customers",customerService.getAll());
    return "admin/customer-list";
    }
    @GetMapping("/create")
    public String createForm (Model model)
    {
        model.addAttribute("customer",new Customer());
        return "admin/customer-form";
    }
    @GetMapping("/edit/{id}")
    public String editForm (Model model, @PathVariable Long id)
    {
        model.addAttribute("customer",customerService.getCustomerById(id));
        return "admin/customer-form";
    }
    @PostMapping("/create")
    public String saveCustomer(@ModelAttribute("customer")Customer customer)
    {
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }
    @PostMapping("/edit/{id}")
    public String updateCustomer (@ModelAttribute("customer")Customer customer ,@PathVariable Long id)
    {
        customer.setId(id);
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }
    public String deleteCustomer(@PathVariable Long id )
    {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
