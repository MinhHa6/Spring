package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.dto.productRequest.ProductUpdateRequest;
import com.vuminhha.decorstore.entity.*;
import com.vuminhha.decorstore.repository.CartRepository;
import com.vuminhha.decorstore.repository.OrderDetailRepository;
import com.vuminhha.decorstore.repository.OrderRepository;
import com.vuminhha.decorstore.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.FileNameMap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private  final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private  final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    public OrderService (OrderRepository orderRepository,CartRepository cartRepository,OrderDetailRepository orderDetailRepository,ProductRepository productRepository)
    {
        this.orderRepository=orderRepository;
        this.cartRepository=cartRepository;
        this.orderDetailRepository=orderDetailRepository;
        this.productRepository=productRepository;
    }

    /**
     * ds don hang
     */
    public List<Order>getAllOrders()
    {
        return orderRepository.findAll();
    }
    /**
     * Lay don hang theo Id
     */
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    /**
     * tao don hang tu gio hang
     */
    @Transactional
    //annotation cua spring quan ly giao dich neu loi thi huy roll back
    public Order createOrderFromCart(Long cartId, PaymentMethod paymentMethod, TransportMethod transportMethod,String receiverName, String address, String email, String phone,
                                     String notes)
    {
        Cart cart=cartRepository.findById(cartId).orElseThrow(()-> new RuntimeException("Cart not found"));
        if(cart.getItems().isEmpty())
        {
            throw  new RuntimeException("Cart is empty");
        }
        // tao order
        Order order= new Order();
        order.setUser(cart.getUser());
        order.setPaymentMethod(paymentMethod);
        order.setTransportMethod(transportMethod);
        order.setOrderDate(LocalDateTime.now());
        order.setReceiverName(receiverName);
        order.setAddress(address);
        order.setEmail(email);
        order.setPhone(phone);
        order.setNotes(notes);
        order.setActive(true);
        order.setDeleted(false);
        order.setTotalMoney(BigDecimal.ZERO);
        orderRepository.save(order);
        BigDecimal total =BigDecimal.ZERO;
        // Duyet gio hang-> chuyen sang order detail
        for (CartItem cartItem:cart.getItems())
        {
            OrderDetail orderDetail= new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetail.setQty(cartItem.getQuantity());
            orderDetail.setTotal(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            total=total.add(orderDetail.getTotal());
            orderDetailRepository.save(orderDetail);
            // tru kho
            Product product=cartItem.getProduct();
            product.setStock(product.getStock()-cartItem.getQuantity());
            productRepository.save(product);
        }
        // cap nhat tong tien
        order.setTotalMoney(total);
        orderRepository.save(order);
        // xoa gio hang
        cart.getItems().clear();
        cartRepository.save(cart);
        return order;
    }
    /**
     * Huy don hang
     */
    @Transactional
    public void cancelOrder(Long orderId)
    {
        Order order=orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
        if (!order.getActive())
        {
            throw  new RuntimeException("Order is already inactive");
        }
        order.setActive(false);
        orderRepository.save(order);
        // Hoan lai ton kho
        for (OrderDetail detail: order.getOrderDetails())
        {
            Product product= detail.getProduct();
            product.setStock(product.getStock()+detail.getQty());
            productRepository.save(product);
        }
    }
    /**
     * Cap nhat trang thai don hang
     */

    @Transactional
    public Order updateOrderStatus(Long orderId,String status)
    {
        Order order= orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException(" Order not found"));
        order.setNotes("Status changed to:"+status);
        orderRepository.save(order);
        return order;
    }

    /**
     * Tim kiem don hang
     */
    public List<Order> searchOrders(String keyword) {
        return orderRepository.findByKeyword(keyword);
    }
}
