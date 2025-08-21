package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.entity.Order;
import com.vuminhha.decorstore.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrdersService ordersService;
    @GetMapping
    public String listOrder(Model model)
    {
        model.addAttribute("orders",ordersService.getAllOrder());
        return "admin/order-list";
    }
    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        model.addAttribute("orders",new Order());
        return "admin/order-form";
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,Model model)
    {
        model.addAttribute("orders",ordersService.getByOrder(id));
        return "admin/order-form";
    }
    @PostMapping("/create")
    public String saveOrder(@ModelAttribute("orders")Order order)
    {
        ordersService.saveOrder(order);
        return "redirect:/orders";
    }
    @PostMapping("/edit/{id}")
  public String updateOrder(@PathVariable Long id,@ModelAttribute("orders")Order order)
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
