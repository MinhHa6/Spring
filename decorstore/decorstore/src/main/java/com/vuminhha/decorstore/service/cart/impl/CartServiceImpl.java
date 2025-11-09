package com.vuminhha.decorstore.service.cart.impl;

import com.vuminhha.decorstore.entity.Cart;
import com.vuminhha.decorstore.entity.CartItem;
import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.entity.User;
import com.vuminhha.decorstore.repository.CartItemRepository;
import com.vuminhha.decorstore.repository.CartRepository;
import com.vuminhha.decorstore.repository.ProductRepository;
import com.vuminhha.decorstore.repository.UserRepository;
import com.vuminhha.decorstore.service.cart.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private  final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository,UserRepository userRepository)
    {
        this.cartRepository=cartRepository;
        this.productRepository=productRepository;
        this.cartItemRepository=cartItemRepository;
        this.userRepository=userRepository;
    }
    // Them san pham vao gio
    @Override
    public void addToCart(Long productId, int qty, HttpSession session, String username) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (username != null) {
            // ĐÃ ĐĂNG NHẬP
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException(" User not found"));

            Cart cart = cartRepository.findByUser_Username(username)
                    .orElseGet(() -> {
                        Cart newCart = new Cart();
                        newCart.setUser(user); // truyền đúng kiểu
                        return cartRepository.save(newCart);
                    });

            // Kiểm tra sản phẩm đã có trong giỏ chưa
            Optional<CartItem> existingItem = cart.getItems().stream()
                    .filter(i -> i.getProduct().getId().equals(productId))
                    .findFirst();

            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + qty);
                cartItemRepository.save(item);
            } else {
                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setProduct(product);
                newItem.setQuantity(qty);
                cartItemRepository.save(newItem);
            }

            cartRepository.save(cart);
            log.info(" Added product {} to cart for user {}", productId, username);

        } else {
            //  CHƯA ĐĂNG NHẬP – lưu trong session
            Map<Long, Integer> sessionCart = (Map<Long, Integer>) session.getAttribute("cart");
            if (sessionCart == null) sessionCart = new HashMap<>();

            sessionCart.put(productId, sessionCart.getOrDefault(productId, 0) + qty);
            session.setAttribute("cart", sessionCart);

            log.info(" Added product {} to SESSION cart", productId);
        }
    }
    // Cap nhat so luong san pham trong gio
    @Override
    public Cart updateQuantity(Long cartId,Long productId,int qty)
    {
        Cart cart= cartRepository.findById(cartId).
                orElseThrow(()-> new RuntimeException("Cart not found"));
        CartItem item = cart.getItems().stream().filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Product not found in cart"));
        item.setQuantity(qty);
        cartItemRepository.save(item);
        log.info("updated qut of product {} in cart {} to {}",productId,cartId,qty);
        return cart;
    }
    // xoa san pham khoi gio
    @Override
    public Cart removeProduct (Long cartId,Long productId )
    {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item-> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
        log.info("Remove product {} from cart {}",productId,cartId);
        return cart;
    }
    // xoa san pham toan bo khoi gio
    @Override
    public void clearCart (Long cartId )
    {
        Cart cart= cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("cart not found "));
        cart.getItems().clear();
        cartRepository.save(cart);
        log.info("Clear cart {}",cartId);
    }
    //  Lấy giỏ hàng theo username (dành cho user đã đăng nhập)
    @Override
    public Cart getCartByUsername(String username) {
        return cartRepository.findByUser_Username(username).orElse(null);
    }
    // Lấy giỏ hàng theo ID
    @Override
    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }
    //  Tạo giỏ hàng mới cho user (nếu chưa có)
    @Override
    public Cart createCartForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user: " + username));

        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setItems(new ArrayList<>());

        return cartRepository.save(newCart);
    }
}
