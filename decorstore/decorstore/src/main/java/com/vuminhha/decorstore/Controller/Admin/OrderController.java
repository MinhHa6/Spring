package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.entity.Order;
import com.vuminhha.decorstore.entity.PaymentMethod;
import com.vuminhha.decorstore.service.CustomerService;
import com.vuminhha.decorstore.service.OrdersService;
import com.vuminhha.decorstore.service.PaymentMethodService;
import com.vuminhha.decorstore.service.TransportMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final CustomerService customerService;
    private final OrdersService ordersService;
    @Autowired
    private  PaymentMethodService paymentMethodService;
    @Autowired
    private TransportMethodService transportMethodService;


    public OrderController(CustomerService customerService, OrdersService ordersService) {
        this.customerService = customerService;
        this.ordersService = ordersService;
    }
    @GetMapping
    public String listOrder(Model model)
    {
        model.addAttribute("orders",ordersService.getAllOrder());
        return "admin/order-list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        model.addAttribute("order",new Order());
        model.addAttribute("customers",customerService.getAll());
        model.addAttribute("paymentMethods",paymentMethodService.getAll());
        model.addAttribute("transportMethods",transportMethodService.getAll());
        return "admin/order-form";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,Model model)
    {
        model.addAttribute("order",ordersService.getByOrder(id));
        model.addAttribute("customers",customerService.getAll());
        model.addAttribute("paymentMethods",paymentMethodService.getAll());
        model.addAttribute("transportMethods",transportMethodService.getAll());
        return "admin/order-form";
    }
    @PostMapping("/create")
    public String saveOrder(@ModelAttribute("order") Order order) {
        Customer cus = customerService.getCustomerById(order.getCustomer().getId());
        order.setEmail(cus.getEmail());
        order.setPhone(cus.getPhone());
        order.setAddress(cus.getAddress());
        order.setNameReciver(cus.getName());
        ordersService.saveOrder(order);
        return "redirect:/orders";
    }
    @PostMapping("/edit/{id}")
  public String updateOrder(@PathVariable Long id,@ModelAttribute("order")Order order)
    {
        order.setId(id);
        ordersService.saveOrder(order);
        return "redirect:/orders";
    }
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id)
    {
        ordersService.deleteOrder(id);
        return "redirect:/orders";
    }
}
