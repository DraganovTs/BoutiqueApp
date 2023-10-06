package com.homecode.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private String errorMessage;
    private String errorCode;
}
