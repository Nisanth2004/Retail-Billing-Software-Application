package com.nisanth.billingsoftware.repository;

import com.nisanth.billingsoftware.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderEntityRepository extends JpaRepository<OrderEntity,Long> {
   Optional<OrderEntity> findByOrderId(String orderId);

  List<OrderEntity> findAllByOrderByCreatedAtDesc();
}
