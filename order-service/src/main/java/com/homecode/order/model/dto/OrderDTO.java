package com.homecode.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;

    private BigDecimal totalPrice;

    private String status;

    private ZonedDateTime shipped;

    private PaymentDTO payment;

    private AddressDTO shipmentAddress;

    private Set<OrderItemDTO> orderItems;
}
