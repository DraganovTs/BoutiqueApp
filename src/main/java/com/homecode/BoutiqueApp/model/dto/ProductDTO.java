package com.homecode.BoutiqueApp.model.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private String productStatus;

    private Integer salesCounter;

    private Set<ReviewDTO> reviews;

    private CategoryDTO category;
}
