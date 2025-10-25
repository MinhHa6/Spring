package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT o FROM Order o WHERE o.orderCode LIKE %:keyword%")
    List<Order> findByKeyword(@Param("keyword") String keyword);
}
