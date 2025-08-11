package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
}
