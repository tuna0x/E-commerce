package com.project.ecommerce.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.ecommerce.domain.Order;
import com.project.ecommerce.domain.Payment;
import com.project.ecommerce.repository.OrderRepository;
import com.project.ecommerce.repository.PaymentRepository;
import com.project.ecommerce.ultil.constant.PaymentMethodEnum;
import com.project.ecommerce.ultil.constant.PaymentStatusEnum;
import com.project.ecommerce.ultil.constant.StatusEnum;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public Payment createCODPayment(Long orderId){
        Order order= this.orderService.getOrder(orderId);
        Payment payment=new Payment();
        payment.setOrder(order);
        payment.setMethod(PaymentMethodEnum.COD);
        payment.setStatus(StatusEnum.PENDDING);
        payment.setAmount(order.getTotalPrice());
        order.setPayment(payment);

        return this.paymentRepository.save(payment);
    }

    public Payment markAsPaid(String transactionId){
        Payment payment=this.paymentRepository.findByTransactionId(transactionId);
        if (payment != null) {
        payment.setStatus(StatusEnum.COMPLETED);

        Order order= payment.getOrder();
        order.setPaymentStatus(PaymentStatusEnum.PAID);
        order.setStatus(StatusEnum.COMPLETED);
        this.orderRepository.save(order);
        }
        return this.paymentRepository.save(payment);
    }

}
