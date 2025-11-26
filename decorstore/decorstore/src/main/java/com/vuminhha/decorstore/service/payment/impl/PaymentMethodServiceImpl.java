package com.vuminhha.decorstore.service.payment.impl;

import com.vuminhha.decorstore.config.exception.ResourceNotFoundException;
import com.vuminhha.decorstore.entity.PaymentMethod;
import com.vuminhha.decorstore.repository.PaymentMethodRepository;
import com.vuminhha.decorstore.service.payment.PaymentMethodService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PaymentMethodServiceImpl implements PaymentMethodService {
     PaymentMethodRepository paymentMethodRepository;
    public List<PaymentMethod> getAll()
    {
        return paymentMethodRepository.findAll();
    }
    public PaymentMethod getById(Long id )
    {
        return paymentMethodRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Payment method not found with id:"+id));
    }
    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod)
    {
        return paymentMethodRepository.save(paymentMethod);
    }
    public void deletePaymentMethod(Long id )
    {
        if (!paymentMethodRepository.existsById(id))
        {
            throw  new ResourceNotFoundException("PaymentMethod not found with id:"+id);
        }
        paymentMethodRepository.deleteById(id);
    }
    public List<PaymentMethod> searchByName(String keyword)
    {
        return paymentMethodRepository.findByNameContainingIgnoreCase(keyword);
    }
}
