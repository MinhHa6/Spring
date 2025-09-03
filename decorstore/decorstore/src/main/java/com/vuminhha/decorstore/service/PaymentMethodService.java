package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.PaymentMethod;
import com.vuminhha.decorstore.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    public List<PaymentMethod> getAll()
    {
        return paymentMethodRepository.findAll();
    }
    // lay phuong thuc thanh toan theo dia chi id
    public PaymentMethod getById(Long id )
    {
        return paymentMethodRepository.findById(id).orElseThrow(()->new RuntimeException("null"));
    }
    // update
    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod)
    {
        return paymentMethodRepository.save(paymentMethod);
    }
    // xoa
    public void deletePaymentMethod(Long id )
    {
        paymentMethodRepository.deleteById(id);
    }
}
