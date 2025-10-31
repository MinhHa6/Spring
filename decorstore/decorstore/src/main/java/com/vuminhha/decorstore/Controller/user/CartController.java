package com.vuminhha.decorstore.Controller.user;

import com.vuminhha.decorstore.entity.Cart;
import com.vuminhha.decorstore.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);

    private final CartService cartService;
    public CartController (CartService cartService)
    {
        this.cartService=cartService;
    }

    /**
     * Thêm sản phẩm vào giỏ hàng (AJAX)
     */
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestParam Long productId,
            @RequestParam int quantity,
            @AuthenticationPrincipal UserDetails userDetails) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Kiểm tra user đã đăng nhập chưa
            if (userDetails == null) {
                response.put("success", false);
                response.put("message", "Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng!");
                response.put("redirect", "/login");
                return ResponseEntity.ok(response);
            }

            // Lấy thông tin user và cart
            // Giả sử bạn có method để lấy User từ UserDetails
            Long cartId = getCurrentUserCartId(userDetails);

            // Thêm sản phẩm vào giỏ
            Cart cart = cartService.addProductCart(cartId, productId, quantity);

            // Tính tổng số lượng items trong giỏ
            int totalItems = cart.getItems().stream()
                    .mapToInt(item -> item.getQuantity())
                    .sum();

            response.put("success", true);
            response.put("message", "Đã thêm sản phẩm vào giỏ hàng!");
            response.put("cartCount", totalItems);

            log.info("User {} added product {} (qty: {}) to cart",
                    userDetails.getUsername(), productId, quantity);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            log.error("Error adding product to cart: ", e);
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            log.error("Unexpected error: ", e);
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
            Cart cart = cartService.addProductCart(cartId, productId, 1);
            log.info("Increased quantity for product {}", productId);
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
