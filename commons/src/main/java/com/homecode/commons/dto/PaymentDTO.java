package com.homecode.commons.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {


    private Long id;

    private String paypalPaymentId;

    private String status;

    private Long orderId;
}
