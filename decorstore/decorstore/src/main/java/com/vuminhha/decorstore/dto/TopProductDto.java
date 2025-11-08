package com.vuminhha.decorstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
public class TopProductDto {
    private Long productId;
    private String productName;
    private String imageUrl;
    private Long totalSold;
    private BigDecimal revenue;

    public TopProductDto(Long productId, String productName, String imageUrl, Long totalSold, BigDecimal revenue) {
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.totalSold = totalSold;
        this.revenue = revenue;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(Long totalSold) {
        this.totalSold = totalSold;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }
}
