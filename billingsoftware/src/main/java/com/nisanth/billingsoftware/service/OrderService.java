package com.nisanth.billingsoftware.service;

import com.nisanth.billingsoftware.io.OrderRequest;
import com.nisanth.billingsoftware.io.OrderResponse;

import java.util.List;

public interface OrderService {
   OrderResponse createOrder(OrderRequest request);
  void deleteOrder(String orderId);
  List<OrderResponse> getlatestOrders();
}
