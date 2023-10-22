package com.homecode.customer.model;

import com.homecode.commons.module.domain.AbstractEntity;
import com.homecode.customer.model.enums.CartStatus;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "cart")
public class Cart extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "order_id")
    private Long orderId;
    @ManyToOne
    private Customer customer;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CartStatus status;

    public Cart(Customer customer) {
        this.customer = customer;
        this.status = CartStatus.NEW;
    }

}
