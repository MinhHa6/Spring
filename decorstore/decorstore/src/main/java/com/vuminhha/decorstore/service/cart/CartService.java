package com.vuminhha.decorstore.service.cart;

import com.vuminhha.decorstore.entity.Cart;
import jakarta.servlet.http.HttpSession;

public interface CartService {
    void addToCart(Long productId, int qty, HttpSession session, String username);
    Cart updateQuantity(Long cartId, Long productId, int qty);
    Cart removeProduct (Long cartId,Long productId );
    void clearCart (Long cartId );
    Cart getCartByUsername(String username);
    Cart getCartById(Long id);
    Cart createCartForUser(String username);
}
