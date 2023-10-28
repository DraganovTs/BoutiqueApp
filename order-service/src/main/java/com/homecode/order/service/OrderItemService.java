package com.homecode.order.service;




import com.homecode.commons.module.dto.OrderItemDTO;
import com.homecode.commons.module.exception.CustomAlreadyExistException;
import com.homecode.commons.module.exception.CustomDatabaseOperationException;
import com.homecode.commons.module.exception.CustomNotFoundException;
import com.homecode.commons.module.exception.CustomValidationException;
import com.homecode.order.model.Order;
import com.homecode.order.model.OrderItem;
import com.homecode.order.repository.OrderItemRepository;
import com.homecode.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<List<OrderItemDTO>> findAll() {
        log.debug("Request to get all OrderItems");

        List<OrderItemDTO> orderItems = this.orderItemRepository.findAll()
                .stream()
                .map(OrderItemService::mapToDto)
                .collect(Collectors.toList());

        if (orderItems.isEmpty()) {
            throw new CustomNotFoundException("No order items available.",
                    "ORDER_ITEMS_NOT_FOUND");
        }

        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<OrderItemDTO> findById(Long id) {
        log.debug("Request to get OrderItem by id : {}", id);

        OrderItemDTO orderItem = this.orderItemRepository.findById(id).map(OrderItemService::mapToDto).orElse(null);
        if (orderItem == null) {
            throw new CustomNotFoundException("Not found order item whit id " + id,
                    "ORDER_ITEM_NOT_FOUND");
        }
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    public ResponseEntity<OrderItemDTO> create(OrderItemDTO orderItemDTO) {
        log.debug("Request to create OrderItem : {}", orderItemDTO);

        if (this.orderItemRepository.findByProductId(orderItemDTO.getProductId()).isPresent()) {
            throw new CustomAlreadyExistException("Order item already exist",
                    "ORDER_ITEM_EXIST");
        }

        Order order = this.orderRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> new CustomNotFoundException("Not found order item whit id " + orderItemDTO.getOrderId(),
                        "ORDER_ITEM_NOT_FOUND"));

        try {
            OrderItem orderItem = mapToOrderItem(orderItemDTO, order);
            this.orderItemRepository.save(orderItem);
            return new ResponseEntity<>(mapToDto(orderItem), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomDatabaseOperationException("An error occurred while creating order item"
                    , "DATABASE_OPERATION_EXCEPTION");
        }

    }


    public ResponseEntity<?> delete(Long id) {
        log.debug("Request to delete OrderItem : {}", id);

        Optional<OrderItem> orderItemOptional = this.orderItemRepository.findById(id);
        if (orderItemOptional.isEmpty()){
            throw new CustomNotFoundException("Not found order item whit id " + id,
                    "ORDER_ITEM_NOT_FOUND");
        }
        try {
            this.orderItemRepository.deleteById(id);
            return new ResponseEntity<>("Delete order item whit id " + id, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomDatabaseOperationException("An error occurred while deleting order item with ID: " + id,
                    "DATABASE_OPERATION_EXCEPTION");
        }
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

    private OrderItem mapToOrderItem(OrderItemDTO orderItemDTO, Order order) {
        if (orderItemDTO == null || order == null) {
            throw new CustomValidationException("Not valid order item",
                    "ORDER_ITEM_NOT_VALID");
        }
        return new OrderItem(
                orderItemDTO.getQuantity(),
                orderItemDTO.getProductId(),
                order
        );
    }
}
