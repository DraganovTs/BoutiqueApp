package com.homecode.order.service;


import com.homecode.commons.dto.OrderItemDTO;
import com.homecode.order.model.Order;
import com.homecode.order.model.OrderItem;
import com.homecode.order.repository.OrderItemRepository;
import com.homecode.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<OrderItemDTO> findAll() {
        log.debug("Request to get all OrderItems");
        return this.orderItemRepository.findAll()
                .stream()
                .map(OrderItemService::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderItemDTO findById(Long id) {
        log.debug("Request to get OrderItem by id : {}", id);
        return this.orderItemRepository.findById(id).map(OrderItemService::mapToDto).orElse(null);
    }

    public OrderItemDTO create(OrderItemDTO orderItemDTO) {
        log.debug("Request to create OrderItem : {}", orderItemDTO);
        Order order = this.orderRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> new IllegalStateException("The order does not exist!!!"));

        return mapToDto(
                this.orderItemRepository.save(
                        new OrderItem(
                                orderItemDTO.getQuantity(),
                                orderItemDTO.getProductId(),
                                order
                        )
                )
        );
    }

    public void delete(Long id) {
        log.debug("Request to delete OrderItem : {}", id);
        this.orderItemRepository.deleteById(id);
    }

    public static OrderItemDTO mapToDto(OrderItem orderItem) {
        if (orderItem != null) {
            return new OrderItemDTO(
                    orderItem.getId(),
                    orderItem.getQuantity(),
                    orderItem.getProductId(),
                    orderItem.getOrder().getId());
        }
        return null;
    }
}
