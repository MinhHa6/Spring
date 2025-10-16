package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.PaymentMethod;
import com.vuminhha.decorstore.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository)
    {
        this.paymentMethodRepository=paymentMethodRepository;
    }
    // lay tat ca phuong thuc thanh toan
    public List<PaymentMethod> getAll()
    {
        return paymentMethodRepository.findAll();
    }
    // lay phuong thuc thanh toan theo  id
    public PaymentMethod getById(Long id )
    {
        return paymentMethodRepository.findById(id).orElseThrow(()->new RuntimeException("Payment method not found with id:"+id));
    }
    // Them cap nhat phuong thuc thanh toan
    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod)
    {
        return paymentMethodRepository.save(paymentMethod);
    }
    // xoa phuong thuc thanh toan theo id
    public void deletePaymentMethod(Long id )
    {
        if (!paymentMethodRepository.existsById(id))
        {
            throw  new RuntimeException("PaymentMethod not found with id:"+id);
        }
        paymentMethodRepository.deleteById(id);
    }
    // tim kiem phuong thuc thanh toan theo ten
    public List<PaymentMethod> searchByName(String keyword)
    {
        return paymentMethodRepository.findByNameContainingIgnoreCase(keyword);
    }
}
