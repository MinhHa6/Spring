package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OderRepository extends JpaRepository<Order,Long> {
}
