package com.homecode.BoutiqueApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private Long orderId;
    private CustomerDTO customerDTO;
    private String status;
}
