package com.vuminhha.decorstore.Controller.admin;

import com.vuminhha.decorstore.entity.Order;
import com.vuminhha.decorstore.entity.PaymentMethod;
import com.vuminhha.decorstore.entity.TransportMethod;
import com.vuminhha.decorstore.service.order.OrderService;
import com.vuminhha.decorstore.service.payment.PaymentMethodService;
import com.vuminhha.decorstore.service.transport.TransportMethodService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final PaymentMethodService paymentMethodService;
    private final TransportMethodService transportMethodService;
    public OrderController (OrderService orderService,TransportMethodService transportMethodService,PaymentMethodService paymentMethodService)
    {
        this.orderService=orderService;
        this.paymentMethodService=paymentMethodService;
        this.transportMethodService=transportMethodService;
    }
    /**
     * Hien thi ds don hang
     */
    @GetMapping
    public String listOrders(Model model)
    {
        model.addAttribute("orders",orderService.getAllOrders());
        return "admin/order-list";
    }
    /**
     * Xem chi tiet don hang
     */
    @GetMapping("/{orderId}")
    public String viewOrderDetail(@PathVariable Long orderId,Model model)
    {
        Order order= orderService.getOrderById(orderId);
        model.addAttribute("order",order);
        return "admin/order-detail";
    }
    /**
     * tao don hang moi tu gio hang
     */
    @PostMapping("/create")
    public String createOrder(@RequestParam Long cartId,@RequestParam Long paymentId,
                              @RequestParam Long transportId,@RequestParam String receiverName,
                              @RequestParam String address,@RequestParam String email,
                              @RequestParam String phone,
                              @RequestParam(required = false) String notes,Model model)
    {
        try
        {
            PaymentMethod paymentMethod=paymentMethodService.getById(paymentId);
            if (paymentMethod == null) {
                throw new RuntimeException("Payment method not found");
            }
            TransportMethod transportMethod=transportMethodService.getById(transportId);
            if(transportMethod == null)
            {
                throw new RuntimeException("Transport not found");
            }
            Order order=orderService.createOrderFromCart(cartId,paymentMethod,transportMethod,receiverName,address,email,phone,notes);
            model.addAttribute("success","Tao don hang thanh cong! Ma don:"+order.getOrderCode());
        }catch (Exception e)
        {
            model.addAttribute("err","Loi khi tao don hang:"+e.getMessage());
        }
        return "redirect:/orders";
    }
    /**
     * Huy don hang
     */
    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId,Model model)
    {
        try
        {
            orderService.cancelOrder(orderId);
            model.addAttribute("success","Huy don hang thanh cong");
        }
        catch (Exception e) {
            model.addAttribute("err", "Loi khi huy don hang:" + e.getMessage());
        }
        return "redirect:/orders";
    }
    /**
     *  Cap nhat trang thai don hang
     */
    @PostMapping("/update/{orderId}")
    public  String updateOrder(@PathVariable Long orderId, @RequestParam String status, Model model)
    {
        {
            try {
                orderService.updateOrderStatus(orderId,status);
                model.addAttribute("success","Cap nhat trang thai don hang thanh cong");
            }
            catch (Exception e)
            {
                model.addAttribute("err","Loi khi cap nhat trang thai don hang:"+e.getMessage());
            }
        }
        return "redirect:/orders"+orderId;
    }
    /**
     * Tim kiem don hang
     */
    @GetMapping("/search")
    public String searchOrders(@RequestParam("keyword") String keyword, Model model) {
        List<Order> orders = orderService.searchOrders(keyword);
        model.addAttribute("orders", orders);
        return "admin/order-list";
    }

}
