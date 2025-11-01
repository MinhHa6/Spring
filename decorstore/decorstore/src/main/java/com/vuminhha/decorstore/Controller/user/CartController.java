package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.entity.Cart;
import com.vuminhha.decorstore.entity.CartItem;
import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.service.CartService;
import com.vuminhha.decorstore.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
@Slf4j
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartService cartService;
    private final ProductService productService;
    public CartController (CartService cartService,ProductService productService)
    {
        this.cartService=cartService;
        this.productService=productService;
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
                        item.getTotal();
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
     * Tăng số lượng sản phẩm
     */
    @GetMapping("/plus/{productId}")
    public String plusQuantity(@PathVariable Long productId,
                               @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long cartId = getCurrentUserCartId(userDetails);
            Cart cart = cartService.getCartById(cartId);

            Optional<CartItem> itemOpt = cart.getItems().stream()
                    .filter(i -> i.getProduct().getId().equals(productId))
                    .findFirst();

            if (itemOpt.isPresent()) {
                CartItem item = itemOpt.get();
                int newQty = item.getQuantity() + 1;
                cartService.updateQuantity(cartId, productId, newQty);
                log.info("Increased quantity for product {} to {}", productId, newQty);
            }

        } catch (Exception e) {
            log.error("Error increasing quantity: ", e);
        }
        return "redirect:/cart";
    }
    /**
     * Giảm số lượng sản phẩm
     */
    @GetMapping("/minus/{productId}")
    public String minusQuantity(@PathVariable Long productId,
                                @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long cartId = getCurrentUserCartId(userDetails);
            // Lấy số lượng hiện tại và giảm đi 1
            Cart cart = cartService.updateQuantity(cartId, productId, -1);
            log.info("Decreased quantity for product {}", productId);
        } catch (Exception e) {
            log.error("Error decreasing quantity: ", e);
        }
        return "redirect:/cart";
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long cartId = getCurrentUserCartId(userDetails);
            cartService.removeProduct(cartId, productId);
            log.info("Removed product {} from cart", productId);
        } catch (Exception e) {
            log.error("Error removing product: ", e);
        }
        return "redirect:/cart";
    }

    /**
     * Xóa toàn bộ giỏ hàng
     */
    @PostMapping("/clear")
    public String clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long cartId = getCurrentUserCartId(userDetails);
            cartService.clearCart(cartId);
            log.info("Cleared cart");
        } catch (Exception e) {
            log.error("Error clearing cart: ", e);
        }
        return "redirect:/cart";
    }

    /**
     * Helper method để lấy cartId của user hiện tại
     * Bạn cần implement method này dựa vào cấu trúc User của bạn
     */
    private Long getCurrentUserCartId(UserDetails userDetails) {
        // TODO: Implement logic để lấy cartId từ user
        // Ví dụ:
        // User user = userRepository.findByUsername(userDetails.getUsername());
        // return user.getCart().getId();

        // Tạm thời return 1L, bạn cần thay đổi logic này
        throw new UnsupportedOperationException("Please implement getCurrentUserCartId method");
    }
}
