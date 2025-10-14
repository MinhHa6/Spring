package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {
    // tim kiem phuong thuc thanh toan theo ten
    List<PaymentMethod> findByNameContainingIgnoreCase(String name);
}
