package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    // user trong cart
    Optional<Cart> findByUser_Username(String username);
}
