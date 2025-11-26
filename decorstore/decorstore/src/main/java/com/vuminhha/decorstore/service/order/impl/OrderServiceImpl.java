package com.vuminhha.decorstore.service.order.impl;

import com.vuminhha.decorstore.entity.*;
import com.vuminhha.decorstore.repository.CartRepository;
import com.vuminhha.decorstore.repository.OrderDetailRepository;
import com.vuminhha.decorstore.repository.OrderRepository;
import com.vuminhha.decorstore.repository.ProductRepository;
import com.vuminhha.decorstore.service.order.OrderService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderServiceImpl implements OrderService {
     OrderRepository orderRepository;
     CartRepository cartRepository;
     OrderDetailRepository orderDetailRepository;
     ProductRepository productRepository;

    /**
     * Danh sách tất cả đơn hàng
     */
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     *  Lấy đơn hàng theo ID (THÊM METHOD NÀY)
     */
    @Override
    public Order getOrderById(Long orderId) {
        Order order=orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        order.getOrderDetails().size(); // Trigger lazy loading
        order.getOrderDetails().forEach(detail -> {
            detail.getProduct().getName(); // Trigger lazy loading for product
        });

        return order;
    }

    /**
     * Lấy đơn hàng theo order code
     */
    @Override
    public Order getOrderByCode(String orderCode) {
        Order order= orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Order not found with code: " + orderCode));
        order.getOrderDetails().size();
        order.getOrderDetails().forEach(detail -> {
            detail.getProduct().getName();
        });

        return order;
    }

    /**
     *  Lấy danh sách đơn hàng theo userId (SỬA LẠI TÊN METHOD)
     */
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        //  createdDate thay vì createDate
        return orderRepository.findByUserIdOrderByCreatedDateDesc(userId);
    }

    /**
     * Tạo đơn hàng từ giỏ hàng
     */
    @Transactional
    @Override
    public Order createOrderFromCart(Long cartId, PaymentMethod paymentMethod,
                                     TransportMethod transportMethod, String receiverName,
                                     String address, String email, String phone, String notes) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Tạo order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setPaymentMethod(paymentMethod);
        order.setTransportMethod(transportMethod);
        order.setOrderDate(LocalDateTime.now());
        order.setReceiverName(receiverName);
        order.setAddress(address);
        order.setEmail(email);
        order.setPhone(phone);
        order.setNotes(notes);
        order.setIsActive(true);
        order.setIsDeleted(false);
        order.setTotalMoney(BigDecimal.ZERO);
        orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;

        // Duyệt giỏ hàng -> chuyển sang order detail
        for (CartItem cartItem : cart.getItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetail.setQty(cartItem.getQuantity());
            orderDetail.setTotal(cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            total = total.add(orderDetail.getTotal());
            orderDetailRepository.save(orderDetail);

            // Trừ kho
            Product product = cartItem.getProduct();
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        // Cập nhật tổng tiền
        order.setTotalMoney(total);
        orderRepository.save(order);

        // Xóa giỏ hàng
        cart.getItems().clear();
        cartRepository.save(cart);

        return order;
    }

    /**
     * Hủy đơn hàng
     */
    @Transactional
    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getIsActive()) {
            throw new RuntimeException("Order is already inactive");
        }

        order.setIsActive(false);
        orderRepository.save(order);

        // Hoàn lại tồn kho
        for (OrderDetail detail : order.getOrderDetails()) {
            Product product = detail.getProduct();
            product.setStock(product.getStock() + detail.getQty());
            productRepository.save(product);
        }
    }

    /**
     * Cập nhật trạng thái đơn hàng
     */
    @Transactional
    @Override
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setNotes("Status changed to: " + status);
        orderRepository.save(order);
        return order;
    }

    /**
     * Tìm kiếm đơn hàng
     */
    @Override
    public List<Order> searchOrders(String keyword) {
        return orderRepository.findByKeyword(keyword);
    }
//    /**
//     * xoa don hang trong lich su don hang nguoi dung
//     */
//    public void deleteByUser(Long orderId,String username)
//    {
//        Order order=orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Khong tim thay don hang"));
//        // Kiem tra quyen
//        if(!order.getUser().getUsername().equals(username))
//        {
//            throw  new RuntimeException("Khong co quyen xoa don nay");
//
//        }
//        order.setDeletedByUser(true);
//        orderRepository.save(order);
//    }
}
