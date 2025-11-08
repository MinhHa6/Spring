package com.vuminhha.decorstore.repository;

import com.vuminhha.decorstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    /**
     * Thong ke
     */
    // Dem tong so don hang khong tinh don hang da xoa
    @Query("SELECT COUNT(o) FROM Order o WHERE o.isDeleted = false")
    long countAllOrders();
    // Dem don hang da huy
    @Query("SELECT COUNT(o) FROM Order o WHERE o.isActive = false AND o.isDeleted = false")
    long countCancelledOrders();
    // Tính doanh thu trong khoảng thời gian
    @Query("SELECT COALESCE(SUM(o.totalMoney), 0) FROM Order o " +
            "WHERE o.isActive = true AND o.isDeleted = false " +
            "AND o.createdDate BETWEEN :startDate AND :endDate")
    BigDecimal calculateRevenueByDateRange(@Param("startDate") LocalDateTime startDate,
                                           @Param("endDate") LocalDateTime endDate);
    // Tính tổng doanh thu (chỉ đơn hàng active)
    @Query("SELECT COALESCE(SUM(o.totalMoney), 0) FROM Order o " +
            "WHERE o.isActive = true AND o.isDeleted = false")
    BigDecimal calculateTotalRevenue();
    // Doanh thu theo thang(12 thang gan nhat)
    @Query("SELECT MONTH(o.createdDate) as month, YEAR(o.createdDate) as year, " +
            "COALESCE(SUM(o.totalMoney), 0) as total, COUNT(o) as orderCount " +
            "FROM Order o " +
            "WHERE o.isActive = true AND o.isDeleted = false " +
            "AND o.createdDate >= :startDate " +
            "GROUP BY YEAR(o.createdDate), MONTH(o.createdDate) " +
            "ORDER BY YEAR(o.createdDate) DESC, MONTH(o.createdDate) DESC")
    List<Object[]> getMonthlyRevenue(@Param("startDate") LocalDateTime startDate);
    // Đếm đơn hàng trong khoảng thời gian
    @Query("SELECT COUNT(o) FROM Order o WHERE o.isDeleted = false " +
            "AND o.createdDate BETWEEN :startDate AND :endDate")
    long countByDateRange(@Param("startDate") LocalDateTime startDate,
                          @Param("endDate") LocalDateTime endDate);
    // Đếm đơn hàng hoạt động
    @Query("SELECT COUNT(o) FROM Order o WHERE o.isActive = true AND o.isDeleted = false")
    long countActiveOrders();
}
