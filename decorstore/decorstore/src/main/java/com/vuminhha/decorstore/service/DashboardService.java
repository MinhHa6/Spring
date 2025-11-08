package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.dto.*;
import com.vuminhha.decorstore.entity.Customer;
import com.vuminhha.decorstore.repository.CustomerRepository;
import com.vuminhha.decorstore.repository.OrderDetailRepository;
import com.vuminhha.decorstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DashboardService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public DashboardStatisticsDto getDashboardStatistics() {
        DashboardStatisticsDto stats = new DashboardStatisticsDto();
        LocalDateTime now = LocalDateTime.now();

        // 1. Thống kê khách hàng
        stats.setTotalCustomers(customerRepository.countAllCustomers());

        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        stats.setNewCustomersThisMonth(
                customerRepository.countNewCustomers(startOfMonth, now)
        );

        // 2. Thống kê doanh thu
        stats.setTotalRevenue(orderRepository.calculateTotalRevenue());
        stats.setRevenueThisMonth(
                orderRepository.calculateRevenueByDateRange(startOfMonth, now)
        );

        // Tính tăng trưởng so với tháng trước
        LocalDateTime startOfLastMonth = startOfMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = startOfMonth.minusNanos(1);
        BigDecimal lastMonthRevenue = orderRepository.calculateRevenueByDateRange(
                startOfLastMonth, endOfLastMonth
        );

        if (lastMonthRevenue.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal growth = stats.getRevenueThisMonth()
                    .subtract(lastMonthRevenue)
                    .divide(lastMonthRevenue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            stats.setRevenueGrowth(growth);
        } else {
            stats.setRevenueGrowth(BigDecimal.ZERO);
        }

        // 3. Thống kê đơn hàng
        stats.setTotalOrders(orderRepository.countAllOrders());
        stats.setOrdersThisMonth(orderRepository.countByDateRange(startOfMonth, now));

        // Giá trị đơn hàng trung bình
        if (stats.getTotalOrders() > 0) {
            stats.setAverageOrderValue(
                    stats.getTotalRevenue().divide(
                            BigDecimal.valueOf(stats.getTotalOrders()),
                            2,
                            RoundingMode.HALF_UP
                    )
            );
        } else {
            stats.setAverageOrderValue(BigDecimal.ZERO);
        }

        // 4. Thống kê trạng thái đơn hàng
        OrderStatusStatistics orderStatus = new OrderStatusStatistics();
        orderStatus.setActive(orderRepository.countActiveOrders());
        orderStatus.setCancelled(orderRepository.countCancelledOrders());
        orderStatus.setTotal(stats.getTotalOrders());
        stats.setOrderStatus(orderStatus);

        // 5. Doanh thu 12 tháng gần nhất
        LocalDateTime twelveMonthsAgo = now.minusMonths(12);
        List<Object[]> revenueData = orderRepository.getMonthlyRevenue(twelveMonthsAgo);
        List<MonthlyRevenueDto> monthlyRevenues = revenueData.stream()
                .map(row -> new MonthlyRevenueDto(
                        ((Number) row[0]).intValue(),  // month
                        ((Number) row[1]).intValue(),  // year
                        (BigDecimal) row[2],           // total
                        ((Number) row[3]).longValue()  // orderCount
                ))
                .collect(Collectors.toList());
        stats.setMonthlyRevenue(monthlyRevenues);

        // 6. Top 10 sản phẩm bán chạy (ĐÃ SỬA: thêm getFullImagePath)
        List<Object[]> topProductsData = orderDetailRepository.findTopSellingProducts();
        List<TopProductDto> topProducts = topProductsData.stream()
                .limit(10)
                .map(row -> new TopProductDto(
                        ((Number) row[0]).longValue(),      // productId
                        (String) row[1],                    // productName
                        getFullImagePath((String) row[2]),  // mainImage - ĐÃ THÊM getFullImagePath()
                        ((Number) row[3]).longValue(),      // totalSold
                        (BigDecimal) row[4]                 // revenue
                ))
                .collect(Collectors.toList());
        stats.setTopProducts(topProducts);

        // 7. Khách hàng mới nhất (5 người) (ĐÃ SỬA: thêm getFullImagePath cho avatar)
        List<Customer> recentCustomers = customerRepository.findTop5RecentCustomers();
        List<RecentCustomerDto> recentCustomerDtos = recentCustomers.stream()
                .limit(5)
                .map(customer -> new RecentCustomerDto(
                        customer.getId(),
                        customer.getName(),
                        customer.getUser().getEmail(),
                        customer.getPhone(),
                        customer.getCreatedDate(),
                        getFullImagePath(customer.getAvatar())  // ĐÃ THÊM getFullImagePath()
                ))
                .collect(Collectors.toList());
        stats.setRecentCustomers(recentCustomerDtos);

        return stats;
    }

    /**
     * Lấy doanh thu theo tháng cho biểu đồ
     */
    public List<MonthlyRevenueDto> getMonthlyRevenueChart(int months) {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(months);
        List<Object[]> data = orderRepository.getMonthlyRevenue(startDate);

        return data.stream()
                .map(row -> new MonthlyRevenueDto(
                        ((Number) row[0]).intValue(),
                        ((Number) row[1]).intValue(),
                        (BigDecimal) row[2],
                        ((Number) row[3]).longValue()
                ))
                .collect(Collectors.toList());
    }

    /**
     * Lấy top sản phẩm bán chạy
     */
    public List<TopProductDto> getTopSellingProducts(int limit) {
        List<Object[]> data = orderDetailRepository.findTopSellingProducts();

        return data.stream()
                .limit(limit)
                .map(row -> new TopProductDto(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        getFullImagePath((String) row[2]),  // Đã có getFullImagePath
                        ((Number) row[3]).longValue(),
                        (BigDecimal) row[4]
                ))
                .collect(Collectors.toList());
    }

    /**
     * Helper method: Thêm /uploads/ vào đường dẫn ảnh nếu cần
     */
    private String getFullImagePath(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return "/uploads/no-image.jpg"; // fallback luôn
        }
        if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
            return imagePath;
        }
        return "/uploads/" + imagePath;
    }

}