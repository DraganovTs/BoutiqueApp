package com.service.model;

import com.homecode.commons.domain.AbstractEntity;
import com.service.model.enums.CartStatus;
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

    @OneToOne
    @JoinColumn(unique = true)
    private Order order;
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
