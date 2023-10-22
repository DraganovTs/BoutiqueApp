package com.homecode.commons.module.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long id;
    private Long orderId;
    private CustomerDTO customerDTO;
    private String status;
}
