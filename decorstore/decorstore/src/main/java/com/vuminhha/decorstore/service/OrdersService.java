package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Order;
import com.vuminhha.decorstore.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {
@Autowired
    private OrderRepository orderRepository;
// lay ra tat ca cac don hang
   public List<Order> getAllOrder()
   {
       return orderRepository.findAll();
   }
   // lay don hang theo dia chi id
    public Order getByOrder(Long id)
    {
        return orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order null"));
    }
    // update-create
    public Order saveOrder(Order order)
    {
        return orderRepository.save(order);
    }
    // xoa don hang
    public void deleteOrder(Long id )
    {
        orderRepository.deleteById(id);
    }
}
