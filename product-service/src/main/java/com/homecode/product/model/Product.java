package com.homecode.product.model;

import com.homecode.commons.domain.AbstractEntity;
import com.homecode.product.model.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "product")
public class Product extends AbstractEntity {

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;
    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;
    @Column(name = "quantity")
    private Integer quantity;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "product_status")
    private ProductStatus productStatus;
    @Column(name = "sales_counter")
    private Integer salesCounter;
    @OneToMany
    private Set<Review> reviews;
    @ManyToOne
    private Category category;
}
