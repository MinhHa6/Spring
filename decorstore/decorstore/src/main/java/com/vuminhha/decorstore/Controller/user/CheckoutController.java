package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.entity.*;
import com.vuminhha.decorstore.service.CartService;
import com.vuminhha.decorstore.service.PaymentMethodService;
import com.vuminhha.decorstore.service.TransportMethodService;
import com.vuminhha.decorstore.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CheckoutController {
    private final CartService cartService;
    private final TransportMethodService transportMethodService;
    private final PaymentMethodService paymentMethodService;
    private final UserService userService;
    public CheckoutController(CartService cartService,TransportMethodService transportMethodService,PaymentMethodService paymentMethodService,UserService userService)
    {
        this.cartService=cartService;
        this.paymentMethodService=paymentMethodService;
        this.transportMethodService=transportMethodService;
        this.userService=userService;
    }
    @GetMapping("/checkout")
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

        // Chuyển đổi chuỗi ID thành List
        List<Long> selectedProductIds = Arrays.stream(products.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<CartItem> selectedItems = new ArrayList<>();
        BigDecimal selectedSubTotal = BigDecimal.ZERO;

        String username = principal.getName();
        Cart cart = cartService.getCartByUsername(username);

        if (cart != null && cart.getItems() != null) {
            // Lọc chỉ lấy sản phẩm đã chọn
            selectedItems = cart.getItems().stream()
                    .filter(item -> selectedProductIds.contains(item.getProduct().getId()))
                    .collect(Collectors.toList());

            selectedSubTotal = selectedItems.stream()
                    .map(CartItem::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        // Lấy danh sách phương thức thanh toán (active và chưa xóa)
        List<PaymentMethod> paymentMethods = paymentMethodService.getAll();

        //  Lấy danh sách phương thức vận chuyển (active và chưa xóa)
        List<TransportMethod> transportMethods = transportMethodService.getAll();

        // Lấy thông tin user

        // Phí ship mặc định (lấy từ phương thức vận chuyển đầu tiên)
        BigDecimal shipping = transportMethods.isEmpty() ?
                BigDecimal.valueOf(30000) :
                BigDecimal.valueOf(30000); // Bạn có thể thêm field fee vào TransportMethod

        BigDecimal total = selectedSubTotal.add(shipping);

        // Gửi dữ liệu sang view
        model.addAttribute("cartItems", selectedItems);
        model.addAttribute("subTotal", selectedSubTotal);
        model.addAttribute("shipping", shipping);
        model.addAttribute("total", total);
        model.addAttribute("paymentMethods", paymentMethods);
        model.addAttribute("transportMethods", transportMethods);

        return "users/checkout";
    }
}
