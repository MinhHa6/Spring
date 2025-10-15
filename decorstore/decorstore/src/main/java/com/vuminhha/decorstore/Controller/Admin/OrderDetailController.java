package com.vuminhha.decorstore.Controller.Admin;

import com.vuminhha.decorstore.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orderDetails")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;
    // Hiển thị danh sách
    @GetMapping
    public String listOrderDetail(Model model) {
        model.addAttribute("orderDetails", orderDetailService.getAll()); // danh sách => số nhiều
        return "admin/orderDetail-list";
    }

    // Hiển thị form tạo
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("orderDetail", new OrderDetail()); // 1 object => số ít
        return "admin/orderDetail-form";
    }

    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("orderDetail", orderDetailService.getOrderDetailById(id));
        return "admin/orderDetail-form";
    }

    // Xử lý lưu
    @PostMapping("/create")
    public String saveOrderDetail(@ModelAttribute("orderDetail") OrderDetail orderDetail) {
        orderDetailService.saveOrderDetail(orderDetail);
        return "redirect:/orderDetails";
    }

    // Xử lý update
    @PostMapping("/edit/{id}")
    public String updateOrderDetail(@PathVariable Long id,
                                    @ModelAttribute("orderDetail") OrderDetail orderDetail) {
        orderDetail.setId(id);
        orderDetailService.saveOrderDetail(orderDetail);
        return "redirect:/orderDetails";
    }
    @GetMapping("/delete/{id}")
    public String deleteOrderDetail(@PathVariable Long id)
    {
        orderDetailService.deleteOrderDetail(id);
        return "redirect:/orderDetails";
    }
}
