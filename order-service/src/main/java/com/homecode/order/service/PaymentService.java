package com.homecode.order.service;


import com.homecode.commons.dto.PaymentDTO;
import com.homecode.order.model.Order;
import com.homecode.order.model.Payment;
import com.homecode.order.model.enums.PaymentStatus;
import com.homecode.order.repository.OrderRepository;
import com.homecode.order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<PaymentDTO> findAll() {
        log.debug("Request to get all Payments");
        return this.paymentRepository.findAll()
                .stream()
                .map(PaymentService::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaymentDTO findById(Long id) {
        log.debug("Request to get Payment : {}", id);
        return this.paymentRepository.findById(id).map(PaymentService::mapToDTO).orElse(null);
    }

    public PaymentDTO create(PaymentDTO paymentDTO) {
        log.debug("Request to create Payment : {}", paymentDTO);
        Order order = this.orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new IllegalStateException("The Order does not exist!!!"));

        return mapToDTO(this.paymentRepository.save(
                new Payment(
                        paymentDTO.getPaypalPaymentId(),
                        PaymentStatus.valueOf(paymentDTO.getStatus()),
                        order
                )
        ));
    }

    public void delete(Long id) {
        log.debug("Request to delete Payment : {}", id);
        this.paymentRepository.deleteById(id);
    }

    public static PaymentDTO mapToDTO(Payment payment) {
        if (payment != null) {
            return new PaymentDTO(
                    payment.getId(),
                    payment.getPaypalPaymentId(),
                    payment.getStatus().name(),
                    payment.getOrder().getId()
            );
        }
        return null;
    }
}
