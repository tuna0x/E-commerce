package com.project.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.config.VNPAYConfig;
import com.project.ecommerce.domain.Payment;
import com.project.ecommerce.domain.response.Payment.ResPaymentVNPAYDTO;
import com.project.ecommerce.service.PaymentService;
import com.project.ecommerce.ultil.annotation.APIMessage;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



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

        @GetMapping("/payment/vn-pay")
    public ResponseEntity<ResPaymentVNPAYDTO> pay(HttpServletRequest req) {
        return ResponseEntity.ok().body(this.paymentService.createVnPayPayment(req));
    }

            @GetMapping("/payment/vn-pay-callback")
    public ResponseEntity<ResPaymentVNPAYDTO> payCallbackHandler(HttpServletRequest req, @RequestParam String vnp_ResponseCode) {
        String status=req.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return ResponseEntity.ok().body(new ResPaymentVNPAYDTO("00", "Success", ""));
        }
        else{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResPaymentVNPAYDTO("99", "Failed", ""));
        }
    }




}
