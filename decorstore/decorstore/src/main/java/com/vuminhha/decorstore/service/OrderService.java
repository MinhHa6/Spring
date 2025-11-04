package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.*;
import com.vuminhha.decorstore.repository.CartRepository;
import com.vuminhha.decorstore.repository.OrderDetailRepository;
import com.vuminhha.decorstore.repository.OrderRepository;
import com.vuminhha.decorstore.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository,
                        OrderDetailRepository orderDetailRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
    }

    /**
     * Danh sách tất cả đơn hàng
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     *  Lấy đơn hàng theo ID (THÊM METHOD NÀY)
     */
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }

    /**
     * Lấy đơn hàng theo order code
     */
    public Order getOrderByCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new RuntimeException("Order not found with code: " + orderCode));
    }

    /**
     * ✅ Lấy danh sách đơn hàng theo userId (SỬA LẠI TÊN METHOD)
     */
    public List<Order> getOrdersByUserId(Long userId) {
        //  createdDate thay vì createDate
        return orderRepository.findByUserIdOrderByCreatedDateDesc(userId);
    }

    /**
     * Tạo đơn hàng từ giỏ hàng
     */
    @Transactional
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
        order.setActive(true);
        order.setDeleted(false);
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
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getActive()) {
            throw new RuntimeException("Order is already inactive");
        }

        order.setActive(false);
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
    public List<Order> searchOrders(String keyword) {
        return orderRepository.findByKeyword(keyword);
    }
}