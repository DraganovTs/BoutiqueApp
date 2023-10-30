package com.homecode.order.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Long id;

    private Long quantity;

    private Long productId;

    private Long orderId;
}
