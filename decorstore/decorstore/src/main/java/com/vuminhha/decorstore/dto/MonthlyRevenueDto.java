package com.vuminhha.decorstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
public class MonthlyRevenueDto {
    private Integer month;
    private Integer year;
    private BigDecimal total;
    private Long orderCount;

    public MonthlyRevenueDto(Integer month, Integer year, BigDecimal total, Long orderCount) {
        this.month = month;
        this.year = year;
        this.total = total;
        this.orderCount = orderCount;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Long orderCount) {
        this.orderCount = orderCount;
    }
}
