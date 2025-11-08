package com.vuminhha.decorstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

public class DashboardStatisticsDto {
    private Long totalCustomers;
    private Long newCustomersThisMonth;
    private BigDecimal totalRevenue;
    private BigDecimal revenueThisMonth;
    private BigDecimal revenueGrowth; // % so với tháng trước
    private Long totalOrders;
    private Long ordersThisMonth;
    private BigDecimal averageOrderValue;

    private OrderStatusStatistics orderStatus;
    private List<MonthlyRevenueDto>monthlyRevenue;
    private List<TopProductDto> topProducts;
    private List<RecentCustomerDto>recentCustomers;
    public DashboardStatisticsDto () {};
    public Long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(Long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public Long getNewCustomersThisMonth() {
        return newCustomersThisMonth;
    }

    public void setNewCustomersThisMonth(Long newCustomersThisMonth) {
        this.newCustomersThisMonth = newCustomersThisMonth;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getRevenueThisMonth() {
        return revenueThisMonth;
    }

    public void setRevenueThisMonth(BigDecimal revenueThisMonth) {
        this.revenueThisMonth = revenueThisMonth;
    }

    public BigDecimal getRevenueGrowth() {
        return revenueGrowth;
    }

    public void setRevenueGrowth(BigDecimal revenueGrowth) {
        this.revenueGrowth = revenueGrowth;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Long getOrdersThisMonth() {
        return ordersThisMonth;
    }

    public void setOrdersThisMonth(Long ordersThisMonth) {
        this.ordersThisMonth = ordersThisMonth;
    }

    public BigDecimal getAverageOrderValue() {
        return averageOrderValue;
    }

    public void setAverageOrderValue(BigDecimal averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }

    public OrderStatusStatistics getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusStatistics orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<MonthlyRevenueDto> getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(List<MonthlyRevenueDto> monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public List<TopProductDto> getTopProducts() {
        return topProducts;
    }

    public void setTopProducts(List<TopProductDto> topProducts) {
        this.topProducts = topProducts;
    }

    public List<RecentCustomerDto> getRecentCustomers() {
        return recentCustomers;
    }

    public void setRecentCustomers(List<RecentCustomerDto> recentCustomers) {
        this.recentCustomers = recentCustomers;
    }
}
