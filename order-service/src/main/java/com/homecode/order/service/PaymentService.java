package com.homecode.order.service;





import com.homecode.order.exception.CustomAlreadyExistException;
import com.homecode.order.exception.CustomDatabaseOperationException;
import com.homecode.order.exception.CustomNotFoundException;
import com.homecode.order.exception.CustomValidationException;
import com.homecode.order.model.dto.PaymentDTO;
import com.homecode.order.model.entity.Order;
import com.homecode.order.model.entity.Payment;
import com.homecode.order.model.enums.PaymentStatus;
import com.homecode.order.repository.OrderRepository;
import com.homecode.order.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<List<PaymentDTO>> findAll() {
        log.debug("Request to get all Payments");
        List<PaymentDTO> payments = this.paymentRepository.findAll()
                .stream()
                .map(PaymentService::mapToDTO)
                .collect(Collectors.toList());

        if (payments.isEmpty()) {
            throw new CustomNotFoundException("No payments available.",
                    "PAYMENTS_NOT_FOUND");
        }
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<PaymentDTO> findById(Long id) {
        log.debug("Request to get Payment : {}", id);
        PaymentDTO payment = this.paymentRepository.findById(id).map(PaymentService::mapToDTO).orElse(null);
        if (payment == null) {
            throw new CustomNotFoundException("Not found payment whit id " + id,
                    "PAYMENT_NOT_FOUND");
        }
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    public ResponseEntity<PaymentDTO> create(PaymentDTO paymentDTO) {
        log.debug("Request to create Payment : {}", paymentDTO);

        if (this.paymentRepository.findByPaypalPaymentId(paymentDTO.getPaypalPaymentId()).isPresent()) {
            throw new CustomAlreadyExistException("Payment already exist",
                    "PAYMENT_EXIST");
        }

        Order order = this.orderRepository.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new CustomNotFoundException("Not found order whit id " + paymentDTO.getOrderId(),
                        "ORDER_NOT_FOUND"));

        try {
            Payment payment = mapToPayment(paymentDTO, order);
            this.paymentRepository.save(payment);
            return new ResponseEntity<>(mapToDTO(payment), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomDatabaseOperationException("An error occurred while creating payment"
                    , "DATABASE_OPERATION_EXCEPTION");
        }
    }


    public ResponseEntity<?> delete(Long id) {
        log.debug("Request to delete Payment : {}", id);

        Optional<Payment> paymentOptional = this.paymentRepository.findById(id);

        if (paymentOptional.isEmpty()){
            throw new CustomNotFoundException("Not found payment whit id " + id,
                    "PAYMENT_NOT_FOUND");
        }
        try {
            this.paymentRepository.deleteById(id);
            return new ResponseEntity<>("Delete payment whit id " + id, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomDatabaseOperationException("An error occurred while deleting payment with ID: " + id,
                    "DATABASE_OPERATION_EXCEPTION");
        }
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

    private Payment mapToPayment(PaymentDTO paymentDTO, Order order) {
        if (paymentDTO == null || order == null) {
            throw new CustomValidationException("Not valid payment",
                    "PAYMENT_NOT_VALID");
        }
        return new Payment(
                paymentDTO.getPaypalPaymentId(),
                PaymentStatus.valueOf(paymentDTO.getStatus()),
                order
        );
    }
}
