package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Optional<Customer>findByUserId(Long userId);
    // tim kiem khach hang theo dua chi username,email,phone
    @Query("""
        SELECT c FROM Customer c
        WHERE c.isDelete = false AND (
            LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(c.phone) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(c.user.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    """)
    List<Customer> searchCustomers(@Param("keyword") String keyword);
}
