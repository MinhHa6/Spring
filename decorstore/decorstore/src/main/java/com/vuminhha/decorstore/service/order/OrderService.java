package com.vuminhha.decorstore.service.order;


import com.vuminhha.decorstore.entity.Order;
import com.vuminhha.decorstore.entity.PaymentMethod;
import com.vuminhha.decorstore.entity.TransportMethod;

import java.util.List;

public interface OrderService {
    public List<Order> getAllOrders();
    Order getOrderByCode(String orderCode) ;
    List<Order> getOrdersByUserId(Long userId);
    Order createOrderFromCart(Long cartId, PaymentMethod paymentMethod,
                              TransportMethod transportMethod, String receiverName,
                              String address, String email, String phone, String notes);
    void cancelOrder(Long orderId);
    Order updateOrderStatus(Long orderId, String status);
    List<Order> searchOrders(String keyword);
    Order getOrderById(Long orderId);
}