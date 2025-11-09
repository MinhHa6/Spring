package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.entity.*;
import com.vuminhha.decorstore.service.cart.CartService;
import com.vuminhha.decorstore.service.payment.PaymentMethodService;
import com.vuminhha.decorstore.service.transport.TransportMethodService;
import com.vuminhha.decorstore.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@RequestMapping("/checkout")
@Controller
public class CheckoutController {
    @Autowired
    private  CartService cartService;
    @Autowired
    private  PaymentMethodService paymentMethodService;
    @Autowired
    private  TransportMethodService transportMethodService;
    @Autowired
    private UserService userService; // Thêm service này

    private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);

    @GetMapping
    public String checkout(@RequestParam(required = false) String products,
                           Model model,
                           Principal principal,
                           HttpSession session) {

        // ✅ BẮT BUỘC ĐĂNG NHẬP
        if (principal == null) {
            session.setAttribute("redirectAfterLogin", "/checkout?products=" + products);
            return "redirect:/login?message=Vui lòng đăng nhập để tiếp tục thanh toán";
        }

        if (products == null || products.isEmpty()) {
            return "redirect:/cart";
        }

        try {
            // Chuyển đổi chuỗi ID thành List
            List<Long> selectedProductIds = Arrays.stream(products.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            String username = principal.getName();
            Cart cart = cartService.getCartByUsername(username);

            if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
                log.warn("Cart is empty for user: {}", username);
                return "redirect:/cart";
            }

            // Lọc chỉ lấy sản phẩm đã chọn
            List<CartItem> selectedItems = cart.getItems().stream()
                    .filter(item -> selectedProductIds.contains(item.getProduct().getId()))
                    .collect(Collectors.toList());

            if (selectedItems.isEmpty()) {
                log.warn("No selected items found for user: {}", username);
                return "redirect:/cart";
            }

            // Tính tổng tiền
            BigDecimal selectedSubTotal = selectedItems.stream()
                    .map(CartItem::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Lấy danh sách phương thức thanh toán
            List<PaymentMethod> paymentMethods = paymentMethodService.getAll();

            // Lấy danh sách phương thức vận chuyển
            List<TransportMethod> transportMethods = transportMethodService.getAll();

            // Lấy thông tin user
            User user = userService.findByUsername(username);

            // Phí ship mặc định
            BigDecimal shipping = BigDecimal.valueOf(30000);
            BigDecimal total = selectedSubTotal.add(shipping);

            // Gửi dữ liệu sang view
            model.addAttribute("cartItems", selectedItems);
            model.addAttribute("subTotal", selectedSubTotal);
            model.addAttribute("shipping", shipping);
            model.addAttribute("total", total);
            model.addAttribute("paymentMethods", paymentMethods);
            model.addAttribute("transportMethods", transportMethods);
            model.addAttribute("user", user);
            //Tạo chuỗi productIds và quantities
            String productIdsString = selectedItems.stream()
                    .map(item -> String.valueOf(item.getProduct().getId()))
                    .collect(Collectors.joining(","));

            String quantitiesString = selectedItems.stream()
                    .map(item -> String.valueOf(item.getQuantity()))
                    .collect(Collectors.joining(","));

            model.addAttribute("productIdsString", productIdsString);
            model.addAttribute("quantitiesString", quantitiesString);

            log.info("Checkout page loaded for user: {} with {} items", username, selectedItems.size());
            return "users/checkout";

        } catch (Exception e) {
            log.error("Error loading checkout page: ", e);
            return "redirect:/cart?error=checkout_failed";
        }
    }
}
