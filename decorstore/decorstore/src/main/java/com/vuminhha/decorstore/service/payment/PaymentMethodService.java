package com.vuminhha.decorstore.service.payment;


import com.vuminhha.decorstore.entity.PaymentMethod;

import java.util.List;
public interface PaymentMethodService  {
    List<PaymentMethod> getAll();
    PaymentMethod getById(Long id );
    PaymentMethod savePaymentMethod(PaymentMethod paymentMethod);
    void deletePaymentMethod(Long id );
    List<PaymentMethod> searchByName(String keyword);

}
