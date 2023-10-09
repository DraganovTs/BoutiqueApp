package com.homecode.order.service;


import com.homecode.commons.dto.OrderDTO;
import com.homecode.commons.exception.CustomNotFoundException;
import com.homecode.order.model.Order;
import com.homecode.order.model.enums.OrderStatus;
import com.homecode.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

//    public Order create(Cart cart) {
//        log.debug("Request to create  Order whit a Cart : {}", cart);
//        return this.orderRepository.save(
//                new Order(
//                        BigDecimal.ZERO,
//                        OrderStatus.CREATION,
//                        null,
//                        null,
//                        null,
//                        Collections.emptySet(),
//                        cart
//                ));
//
//    }

    public OrderDTO create(OrderDTO orderDTO) {
        log.debug("Request to create Order : {}", orderDTO);
        return mapToDto(this.orderRepository.save(
                new Order(
                        BigDecimal.ZERO,
                        OrderStatus.CREATION,
                        null,
                        null,
                        null,
                        Collections.emptySet(),
                        null
                )
        ));
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<OrderDTO>> findAll() {
        log.debug("Request to get all Orders");

        List<OrderDTO> orders = this.orderRepository.findAll()
                .stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
        if (orders.isEmpty()) {
            throw new CustomNotFoundException("No categories available.",
                    "CATEGORIES_NOT_FOUND");
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAllByUser(Long id) {
        log.debug("Request to get all Orders by Customer id {}", id);
//        return this.orderRepository.findByCustomer_Id(id)
//                .stream()
//                .map(OrderService::mapToDto)
//                .collect(Collectors.toList());
        return null;
        //TODO fix this query
    }

    public OrderDTO findById(Long id) {
        log.debug("Request to get Order by id {}", id);
        return this.orderRepository.findById(id).map(OrderService::mapToDto)
                .orElse(null);
    }

    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        this.orderRepository.deleteById(id);
    }

    public static OrderDTO mapToDto(Order order) {
        if (order != null) {
            return new OrderDTO(
                    order.getId(),
                    order.getTotalPrice(),
                    order.getStatus().name(),
                    order.getShipped(),
                    PaymentService.mapToDTO(order.getPayment()),
                    AddressService.mapToDTO(order.getShipmentAddress()),
                    order.getOrderItems()
                            .stream()
                            .map(OrderItemService::mapToDto)
                            .collect(Collectors.toSet())
            );

        }
        return null;
    }

}
