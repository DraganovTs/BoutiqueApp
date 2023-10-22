package com.homecode.order.model;

import com.homecode.domain.AbstractEntity;
import com.homecode.order.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "payment")
public class Payment extends AbstractEntity {

    @Column(name = "paypal_payment_id")
    private String paypalPaymentId;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private PaymentStatus status;
    @OneToOne
    @JoinColumn(unique = true)
    private Order order;
}
