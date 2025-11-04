package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    // lay theo orderCode
    Optional<Order>findByOrderCode(String orderCode);

    // Tim don hang theo userId ,sap xep theo ngay toa giam fan
    List<Order> findByUserIdOrderByCreatedDateDesc(Long userId);

    @Query("SELECT o FROM Order o WHERE " +
            "o.orderCode LIKE %:keyword% OR " +
            "o.receiverName LIKE %:keyword% OR " +
            "o.phone LIKE %:keyword% OR " +
            "o.email LIKE %:keyword%")
    List<Order> findByKeyword(@Param("keyword") String keyword);
}
