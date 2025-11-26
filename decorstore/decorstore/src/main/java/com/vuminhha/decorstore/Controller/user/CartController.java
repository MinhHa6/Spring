package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.entity.*;
import com.vuminhha.decorstore.service.cart.CartService;
import com.vuminhha.decorstore.service.payment.PaymentMethodService;
import com.vuminhha.decorstore.service.product.ProductService;
import com.vuminhha.decorstore.service.transport.TransportMethodService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;
    private final ProductService productService;
    private final TransportMethodService transportMethodService;
    private final PaymentMethodService paymentMethodService;
    public CartController (CartService cartService,ProductService productService,PaymentMethodService paymentMethodService,
    TransportMethodService transportMethodService)
    {
        this.cartService=cartService;
        this.productService=productService;
        this.paymentMethodService=paymentMethodService;
        this.transportMethodService=transportMethodService;
    }

    @GetMapping
    public String viewCart(Model model, Principal principal, HttpSession session) {
        List<CartItem> cartItems = new ArrayList<>();
        BigDecimal subTotal = BigDecimal.ZERO;

        if (principal != null) {
            // 🔹 Người dùng đã đăng nhập → lấy giỏ hàng trong DB
            String username = principal.getName();
            Cart cart = cartService.getCartByUsername(username);

            if (cart == null) {
                cart = cartService.createCartForUser(username);
            }

            cartItems = (cart.getItems() != null) ? cart.getItems() : new ArrayList<>();
            subTotal = cartItems.stream()
                    .map(item -> item.getTotal() != null ? item.getTotal() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            // 🔹 Người dùng chưa đăng nhập → lấy giỏ hàng từ session
            Map<Long, Integer> sessionCart = (Map<Long, Integer>) session.getAttribute("cart");

            if (sessionCart != null && !sessionCart.isEmpty()) {
                // Lấy sản phẩm từ DB để hiển thị thông tin
                for (Map.Entry<Long, Integer> entry : sessionCart.entrySet()) {
                    Long productId = entry.getKey();
                    int quantity = entry.getValue();

                    Product product = productService.getProductId(productId);
                    if (product != null) {
                        CartItem item = new CartItem();
                        item.setProduct(product);
                        item.setQuantity(quantity);
                        cartItems.add(item);
                    }
                }

                subTotal = cartItems.stream()
                        .map(item -> item.getTotal() != null ? item.getTotal() : BigDecimal.ZERO)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
        }

        // 🔹 Phí vận chuyển & tổng cộng
        BigDecimal shipping = BigDecimal.valueOf(30000);
        BigDecimal total = subTotal.add(shipping);

        // 🔹 Gửi dữ liệu sang view
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subTotal", subTotal);
        model.addAttribute("shipping", shipping);
        model.addAttribute("total", total);
        model.addAttribute("cartCount", cartItems.size());

        return "users/cart";
    }



    /**
     * Thêm sản phẩm vào giỏ hàng (AJAX)
     */
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            @AuthenticationPrincipal UserDetails userDetails,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();

        try {
            String username = (userDetails != null) ? userDetails.getUsername() : null;

            // ✅ Gọi service thêm sản phẩm (cả đăng nhập & chưa đăng nhập)
            cartService.addToCart(productId, quantity, session, username);

            // ✅ Nếu đăng nhập → đếm số lượng sản phẩm trong giỏ DB
            int totalItems = 0;
            if (username != null) {
                Cart cart = cartService.getCartByUsername(username);
                totalItems = (cart != null && cart.getItems() != null)
                        ? cart.getItems().stream().mapToInt(i -> i.getQuantity()).sum()
                        : 0;
            } else {
                // ✅ Nếu chưa đăng nhập → đếm trong session
                Map<Long, Integer> sessionCart = (Map<Long, Integer>) session.getAttribute("cart");
                totalItems = (sessionCart != null)
                        ? sessionCart.values().stream().mapToInt(Integer::intValue).sum()
                        : 0;
            }

            response.put("success", true);
            response.put("message", "🛒 Đã thêm sản phẩm vào giỏ hàng!");
            response.put("cartCount", totalItems);

            log.info("✅ Product {} (qty: {}) added by {}",
                    productId, quantity, (username != null ? username : "guest"));

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            log.error("❌ Lỗi khi thêm sản phẩm vào giỏ hàng: ", e);
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            log.error("⚠️ Lỗi không mong muốn: ", e);
            response.put("success", false);
            response.put("message", "Có lỗi xảy ra. Vui lòng thử lại!");
            return ResponseEntity.internalServerError().body(response);
        }
    }


    /**
     * Update
     */
    @PostMapping("/update")
    public String updateCart(@RequestParam Long id,
                             @RequestParam int quantity,
                             Principal principal,
                             HttpSession session) {
        try {
            if (principal != null) {
                // Đã đăng nhập - cập nhật trong DB
                String username = principal.getName();
                Cart cart = cartService.getCartByUsername(username);
                if (cart != null) {
                    cartService.updateQuantity(cart.getId(), id, quantity);
                }
            } else {
                // Chưa đăng nhập - cập nhật trong session
                Map<Long, Integer> sessionCart = (Map<Long, Integer>) session.getAttribute("cart");
                if (sessionCart != null && sessionCart.containsKey(id)) {
                    if (quantity > 0) {
                        sessionCart.put(id, quantity);
                    } else {
                        sessionCart.remove(id);
                    }
                    session.setAttribute("cart", sessionCart);
                    log.info("Updated session cart: product {} to quantity {}", id, quantity);
                }
            }
        } catch (Exception e) {
            log.error("Error updating cart: ", e);
        }
        return "redirect:/cart";
    }
    /**
     * xoa san pham khoi cart theo id
     */
    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable Long id,
                                 Principal principal,
                                 HttpSession session) {
        try {
            if (principal != null) {
                // Đã đăng nhập
                String username = principal.getName();
                Cart cart = cartService.getCartByUsername(username);
                if (cart != null) {
                    cartService.removeProduct(cart.getId(), id);
                }
            } else {
                // Chưa đăng nhập - xóa khỏi session
                Map<Long, Integer> sessionCart = (Map<Long, Integer>) session.getAttribute("cart");
                if (sessionCart != null) {
                    sessionCart.remove(id);
                    session.setAttribute("cart", sessionCart);
                    log.info("Removed product {} from session cart", id);
                }
            }
        } catch (Exception e) {
            log.error("Error removing product: ", e);
        }
        return "redirect:/cart";
    }

}
