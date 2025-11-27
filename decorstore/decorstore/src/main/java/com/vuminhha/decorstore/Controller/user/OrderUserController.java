package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.entity.*;
import com.vuminhha.decorstore.service.cart.CartService;
import com.vuminhha.decorstore.service.order.OrderService;
import com.vuminhha.decorstore.service.payment.PaymentMethodService;
import com.vuminhha.decorstore.service.transport.TransportMethodService;
import com.vuminhha.decorstore.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderUserController {
    OrderService orderService;
    CartService cartService;
    PaymentMethodService paymentMethodService;
    TransportMethodService transportMethodService;
    UserService userService;
    private static final Logger log = LoggerFactory.getLogger(OrderUserController.class);


    /**
     * xu ly dat hang tu checkout
     */
    @PostMapping("/place")
    public String placeOrder(@RequestParam String fullName,
                             @RequestParam String phone,
                             @RequestParam String email,
                             @RequestParam String address,
                             @RequestParam(required = false) String city,
                             @RequestParam(required = false) String district,
                             @RequestParam(required = false) String ward,
                             @RequestParam(required = false) String note,
                             @RequestParam Long paymentMethodId,
                             @RequestParam Long transportMethodId,
                             @RequestParam String productIds, // Danh sách ID sản phẩm đã chọn
                             @RequestParam String quantities, // Danh sách số lượng
                             Principal principal,
                             HttpSession session,
                             Model model) {

        try {
            // Kiểm tra đăng nhập
            if (principal == null) {
                return "redirect:/login?message=Vui lòng đăng nhập để đặt hàng";
            }

            String username = principal.getName();
            Cart cart = cartService.getCartByUsername(username);

            if (cart == null || cart.getItems().isEmpty()) {
                return "redirect:/cart?error=empty";
            }

            // Lấy phương thức thanh toán và vận chuyển
            PaymentMethod paymentMethod = paymentMethodService.getById(paymentMethodId);
            TransportMethod transportMethod = transportMethodService.getById(transportMethodId);

            // Ghép địa chỉ đầy đủ
            String fullAddress = address;
            if (ward != null && !ward.isEmpty()) {
                fullAddress += ", " + ward;
            }
            if (district != null && !district.isEmpty()) {
                fullAddress += ", " + district;
            }
            if (city != null && !city.isEmpty()) {
                fullAddress += ", " + city;
            }

            // Tạo đơn hàng
            Order order = orderService.createOrderFromCart(
                    cart.getId(),
                    paymentMethod,
                    transportMethod,
                    fullName,
                    fullAddress,
                    email,
                    phone,
                    note
            );

            log.info(" Order created successfully: {} for user: {}", order.getOrderCode(), username);

            // Chuyển đến trang xác nhận đơn hàng
            return "redirect:/order/success?orderCode=" + order.getOrderCode();

        } catch (Exception e) {
            log.error(" Error placing order: ", e);
            model.addAttribute("error", "Có lỗi xảy ra khi đặt hàng: " + e.getMessage());
            return "redirect:/checkout?error=place_order_failed";
        }
    }
    /**
     * Trang xac nhan don hang
     */
    @GetMapping("/success")
    public String orderSuccess(@RequestParam String orderCode, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            Order order = orderService.getOrderByCode(orderCode);

            // Kiểm tra order có thuộc về user hiện tại không
            if (!order.getUser().getUsername().equals(principal.getName())) {
                return "redirect:/home?error=unauthorized";
            }

            model.addAttribute("order", order);
            return "users/order-success";

        } catch (Exception e) {
            log.error("Error loading order success page: ", e);
            return "redirect:/home?error=order_not_found";
        }
    }
    /**
     * Xem lịch sử đơn hàng
     */
    @GetMapping("/history")
    public String orderHistory(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            String username = principal.getName();
            User user = userService.findByUsername(username);

            List<Order> orders = orderService.getOrdersByUserId(user.getId());

            model.addAttribute("orders", orders);
            return "users/order-history";

        } catch (Exception e) {
            log.error("Error loading order history: ", e);
            return "redirect:/home?error=load_orders_failed";
        }
    }

    /**
     * Chi tiết đơn hàng
     */
    @GetMapping("/detail/{orderId}")
    public String orderDetail(@PathVariable Long orderId, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            Order order = orderService.getOrderById(orderId);

            //  Thêm log để debug
            log.info("Order loaded: {}", order.getOrderCode());
            log.info("OrderDetails count: {}", order.getOrderDetails() != null ? order.getOrderDetails().size() : 0);

            if (order.getOrderDetails() != null) {
                order.getOrderDetails().forEach(detail -> {
                    log.info("  - Product: {}, Qty: {}, Price: {}",
                            detail.getProduct().getName(),
                            detail.getQty(),
                            detail.getPrice());
                });
            }

            // Kiểm tra quyền truy cập
            if (!order.getUser().getUsername().equals(principal.getName())) {
                return "redirect:/order/history?error=unauthorized";
            }

            model.addAttribute("order", order);
            return "users/order-detail";

        } catch (Exception e) {
            log.error(" Error loading order detail: ", e);
            e.printStackTrace(); //  In stack trace để debug
            return "redirect:/order/history?error=order_not_found";
        }
    }

    /**
     * Hủy đơn hàng
     */
    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            Order order = orderService.getOrderById(orderId);

            // Kiểm tra quyền
            if (!order.getUser().getUsername().equals(principal.getName())) {
                return "redirect:/order/history?error=unauthorized";
            }

            orderService.cancelOrder(orderId);

            log.info("Order {} cancelled by user: {}", orderId, principal.getName());
            return "redirect:/order/history?success=order_cancelled";

        } catch (Exception e) {
            log.error("Error cancelling order: ", e);
            return "redirect:/order/history?error=cancel_failed";
        }
    }
//    /**
//     * xoa don hang khoi lich su don hang
//     */
//    @PostMapping("/delete/{orderId}")
//    public String deleteOrderFromHistory(@PathVariable Long orderId,Principal principal)
//    {
//        if(principal ==null)
//        {
//            return "redirect:/login";
//        }
//        try {
//            orderService.deleteByUser(orderId, principal.getName());
//            return "redirect:/order/history?success=deleted";
//        }
//        catch (Exception e)
//        {
//            log.error("Error deleting order from user history:",e);
//            return  "redirect:/order/history?error=delete_failed";
//        }
//    }
}
