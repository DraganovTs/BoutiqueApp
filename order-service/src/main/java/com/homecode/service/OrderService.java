package com.homecode.service;


import com.homecode.commons.dto.OrderDTO;
import com.homecode.model.Order;
import com.homecode.model.enums.OrderStatus;
import com.homecode.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Order create(Cart cart) {
        log.debug("Request to create  Order whit a Cart : {}", cart);
        return this.orderRepository.save(
                new Order(
                        BigDecimal.ZERO,
                        OrderStatus.CREATION,
                        null,
                        null,
                        null,
                        Collections.emptySet(),
                        cart
                ));

    }

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
    public List<OrderDTO> findAll() {
        log.debug("Request to get all Orders");
        return this.orderRepository.findAll()
                .stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDTO> findAllByUser(Long id) {
        log.debug("Request to get all Orders by Customer id {}", id);
        return this.orderRepository.findByCartCustomer_Id(id)
                .stream()
                .map(OrderService::mapToDto)
                .collect(Collectors.toList());
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
