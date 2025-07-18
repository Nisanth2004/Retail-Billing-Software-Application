package com.nisanth.billingsoftware.service.impl;

import com.nisanth.billingsoftware.entity.OrderEntity;
import com.nisanth.billingsoftware.entity.OrderItemEntity;
import com.nisanth.billingsoftware.io.*;
import com.nisanth.billingsoftware.repository.OrderEntityRepository;
import com.nisanth.billingsoftware.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderEntityRepository orderEntityRepository;
    @Override
    public OrderResponse createOrder(OrderRequest request) {

      OrderEntity newOrder= convertToOrderEntity(request);

      // create payment details
        PaymentDetails paymentDetails=new PaymentDetails();
        paymentDetails.setStatus(newOrder.getPaymentMethod() == PaymentMethod.CASH?
                PaymentDetails.PaymentStatus.COMPLETED: PaymentDetails.PaymentStatus.PENDING
                );

        newOrder.setPaymentDetails(paymentDetails);
        List<OrderItemEntity> orderItems=request.getCartItems().stream()
                .map(this::convertToOrderItemEntity)
                .collect(Collectors.toList());

        newOrder.setItems(orderItems);

        newOrder = orderEntityRepository.save(newOrder);
        return convertToResponse(newOrder);



    }
    @Override
    public void deleteOrder(String orderId) {

        OrderEntity existingrder=orderEntityRepository.findByOrderId(orderId)
                .orElseThrow(()->new RuntimeException("Order Not found"));
        orderEntityRepository.delete(existingrder);

    }

    @Override
    public List<OrderResponse> getlatestOrders() {
        return orderEntityRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }

    @Override
    public OrderResponse verifyPayment(PaymentVerificationRequest request) {

        // get the existing order
       OrderEntity existingOrder= orderEntityRepository.findByOrderId(request.getOrderId())
                .orElseThrow(()->new RuntimeException("Order Not found"));

       if(!verifyRazorpaySignature(request.getRazorpayOrderId(),
               request.getRazorpayPaymentId(),
               request.getRazorpaySignature()))
       {
           throw new RuntimeException("Payment verification failed");
           
       }

       PaymentDetails paymentDetails=existingOrder.getPaymentDetails();
       paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
       paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
       paymentDetails.setRazorpaySignature(request.getRazorpaySignature());
       paymentDetails.setStatus(PaymentDetails.PaymentStatus.COMPLETED);
       existingOrder=orderEntityRepository.save(existingOrder);

        return convertToResponse(existingOrder);
    }

    @Override
    public Double sumSalesPerDay(LocalDate date) {
        return orderEntityRepository.sumSalesByDate(date);
    }

    @Override
    public Long countByOrderDate(LocalDate date) {
       return orderEntityRepository.countByOrderDate(date);
    }

    @Override
    public List<OrderResponse> findRecentOrders() {
        return orderEntityRepository.findRecentOrders(PageRequest.of(0,10))
                .stream()
                .map(orderEntity -> convertToResponse(orderEntity))
                .collect(Collectors.toList());
    }

    private boolean verifyRazorpaySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {

      return true;
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
       return OrderResponse.builder()
                .orderId(newOrder.getOrderId())
                .customerName(newOrder.getCustomerName())
                .phoneNumber(newOrder.getPhoneNumber())
                .subtotal(newOrder.getSubtotal())
                .tax(newOrder.getTax())
                .grandTotal(newOrder.getGrandTotal())
                .paymentMethod(newOrder.getPaymentMethod())
                .items(newOrder.getItems().stream()
                        .map(this::convertToItemResponse)
                        .collect(Collectors.toList()))
                .paymentDetails(newOrder.getPaymentDetails())
                .createdAt(newOrder.getCreatedAt())
                .build();


    }

    private OrderResponse.OrderItemResponse convertToItemResponse(OrderItemEntity orderItemEntity) {

       return  OrderResponse.OrderItemResponse.builder()
                .itemId(orderItemEntity.getItemId())
                .name(orderItemEntity.getName())
                .price(orderItemEntity.getPrice())
                .quantity(orderItemEntity.getQuantity())
                .build();


    }

    private OrderEntity convertToOrderEntity(OrderRequest request) {

      return  OrderEntity.builder()
                .customerName(request.getCustomerName())
                .phoneNumber(request.getPhoneNumber())
                .subtotal(request.getSubtotal())
                .tax(request.getTax())
                .grandTotal(request.getGrandTotal())
                .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
                .build();
    }

    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest)
    {
        return OrderItemEntity.builder()
                .itemId(orderItemRequest.getItemId())
                .name(orderItemRequest.getName())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .build();


    }
}
