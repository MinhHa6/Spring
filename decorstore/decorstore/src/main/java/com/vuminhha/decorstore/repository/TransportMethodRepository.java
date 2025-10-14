package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.TransportMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransportMethodRepository extends JpaRepository<TransportMethod,Long> {
    // tim kiem phuong thuc van chuyen
    List<TransportMethod> findByNameContainingIgnoreCase(String name);
}
