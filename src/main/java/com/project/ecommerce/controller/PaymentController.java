package com.project.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.domain.Payment;
import com.project.ecommerce.domain.response.Payment.ResPaymentVNPAYDTO;
import com.project.ecommerce.repository.OrderRepository;
import com.project.ecommerce.repository.PaymentRepository;
import com.project.ecommerce.service.PaymentService;
import com.project.ecommerce.ultil.annotation.APIMessage;
import com.project.ecommerce.ultil.constant.PaymentStatusEnum;
import com.project.ecommerce.ultil.constant.StatusEnum;
import com.project.ecommerce.ultil.error.IdInvalidException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PaymentController {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;

    @PostMapping("/payment/confirm")
    @APIMessage("confirm")
    public ResponseEntity<Payment> confirmPayment(@RequestParam String transactionID) {
        return ResponseEntity.ok().body(this.paymentService.markAsPaid(transactionID));
    }

        @GetMapping("/payment/vn-pay")
    public ResponseEntity<ResPaymentVNPAYDTO> pay(HttpServletRequest req,@RequestParam Long paymentId) {
        return ResponseEntity.ok().body(this.paymentService.createVnPayPayment(req,paymentId));
    }

            @GetMapping("/payment/vn-pay-callback")
    public ResponseEntity<ResPaymentVNPAYDTO> payCallbackHandler(HttpServletRequest req, @RequestParam String vnp_ResponseCode) throws IdInvalidException {
        Long paymentId = Long.valueOf(req.getParameter("vnp_TxnRef"));
        String status=req.getParameter("vnp_ResponseCode");
        String transactionId = req.getParameter("vnp_TransactionNo");
        Payment payment= this.paymentService.findById(paymentId);
        if (payment==null) {
            throw new IdInvalidException("Payment not found");
        }
        payment.setTransactionId(transactionId);
        if (status.equals("00")) {
        payment.setStatus(StatusEnum.COMPLETED);
        payment.getOrder().setPaymentStatus(PaymentStatusEnum.PAID);
        this.paymentRepository.save(payment);
        this.orderRepository.save(payment.getOrder());
        return ResponseEntity.ok().body(new ResPaymentVNPAYDTO("00", "Success", ""));
        }
        else{
        payment.setStatus(StatusEnum.FAILED);
        this.paymentRepository.save(payment);
        this.orderRepository.save(payment.getOrder());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResPaymentVNPAYDTO("99", "Failed", ""));
        }

    }




}
