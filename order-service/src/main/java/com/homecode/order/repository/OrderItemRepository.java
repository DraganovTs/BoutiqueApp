package com.homecode.order.repository;

import com.homecode.order.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    Optional<OrderItem> findByProductId(Long productId);
}
