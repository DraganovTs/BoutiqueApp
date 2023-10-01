package com.homecode.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homecode.commons.domain.AbstractEntity;
import com.homecode.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @NotNull
    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private OrderStatus status;
    @Column(name = "shipped")
    private ZonedDateTime shipped;
    @OneToOne
    @JoinColumn(unique = true)
    private Payment payment;
    @Embedded
    private Address shipmentAddress;
    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private Set<OrderItem> orderItems;
    @OneToOne(mappedBy = "order")
    @JsonIgnore
    private Cart cart;
}
