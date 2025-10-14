package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Cart;
import com.vuminhha.decorstore.entity.CartItem;
import com.vuminhha.decorstore.entity.Product;
import com.vuminhha.decorstore.repository.CartItemRepository;
import com.vuminhha.decorstore.repository.CartRepository;
import com.vuminhha.decorstore.repository.ProductRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j

public class CartService {
    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private  final ProductRepository productRepository;

    public CartService (CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository)
    {
        this.cartRepository=cartRepository;
        this.productRepository=productRepository;
        this.cartItemRepository=cartItemRepository;
    }
    // Them san pham vao gio
    public Cart addProductCart(Long cartId,Long productId,int qty)
    {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("Product not found"));
        // Kiem tra xem Product da co trong gio hang chua
        // Kiểm tra xem product đã có trong giỏ chưa
        Optional<CartItem> existingItemOpt = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            // Cập nhật số lượng
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + qty);
            cartItemRepository.save(existingItem);
            log.info("Updated product {} qty to {}", productId, existingItem.getQuantity());
        } else {
            // Thêm mới
            CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(qty);
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
            log.info("Added new product {} to cart {}", productId, cartId);
        }

        return cartRepository.save(cart);
    }
    // Cap nhat so luong san pham trong gio
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
    public void clearCart (Long cartId )
    {
        Cart cart= cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("cart not found "));
        cart.getItems().clear();
        cartRepository.save(cart);
        log.info("Clear cart {}",cartId);
    }
}
