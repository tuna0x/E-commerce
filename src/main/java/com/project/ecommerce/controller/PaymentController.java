package com.project.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.domain.Payment;
import com.project.ecommerce.service.PaymentService;
import com.project.ecommerce.ultil.annotation.APIMessage;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/payment/confirm")
    @APIMessage("confirm")
    public ResponseEntity<Payment> confirmPayment(@RequestParam String transactionID) {
        return ResponseEntity.ok().body(this.paymentService.markAsPaid(transactionID));
    }

}
