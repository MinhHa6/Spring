package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.OrderDetail;
import com.vuminhha.decorstore.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    // lay tat ca chi tiet don hang
    public List<OrderDetail> getAll()
    {
        return orderDetailRepository.findAll();
    }
    // lay chi tiet don hang theo dia chi Id
    public OrderDetail getOrderDetailById(Long id)
    {
        return orderDetailRepository.findById(id).orElseThrow(()->new RuntimeException("OrderDetail Null "));
    }
    // update-create
    public OrderDetail saveOrderDetail (OrderDetail orderDetail)
    {
        return orderDetailRepository.save(orderDetail);
    }
    // xoa orderDetail theo dia chi id
    public void deleteOrderDetail (Long id )
    {
        orderDetailRepository.deleteById(id);
    }
}
