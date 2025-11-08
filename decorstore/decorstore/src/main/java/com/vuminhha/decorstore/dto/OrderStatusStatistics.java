package com.vuminhha.decorstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class OrderStatusStatistics {
    private Long active;// don hang dang hoat dong
    private Long cancelled;// don hang da huy
    private Long total; // tong don hang

    public OrderStatusStatistics() {
    }

    public OrderStatusStatistics(Long active, Long cancelled, Long total) {
        this.active = active;
        this.cancelled = cancelled;
        this.total = total;
    }

    public Long getActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }

    public Long getCancelled() {
        return cancelled;
    }

    public void setCancelled(Long cancelled) {
        this.cancelled = cancelled;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
