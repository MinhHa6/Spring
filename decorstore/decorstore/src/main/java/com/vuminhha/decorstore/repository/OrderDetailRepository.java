package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    // Top sản phẩm bán chạy (chỉ đơn hàng active)
    @Query("SELECT od.product.id, od.product.name, od.product.mainImage, " +
            "SUM(od.qty) as totalSold, SUM(od.total) as revenue " +
            "FROM OrderDetail od " +
            "JOIN od.order o " +
            "WHERE o.isActive = true AND o.isDeleted = false " +
            "GROUP BY od.product.id, od.product.name, od.product.mainImage " +
            "ORDER BY totalSold DESC")
    List<Object[]> findTopSellingProducts();
}
