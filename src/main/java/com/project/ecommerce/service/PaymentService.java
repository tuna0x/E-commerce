package com.project.ecommerce.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.ecommerce.config.VNPAYConfig;
import com.project.ecommerce.domain.Order;
import com.project.ecommerce.domain.Payment;
import com.project.ecommerce.domain.response.Payment.ResPaymentVNPAYDTO;
import com.project.ecommerce.repository.OrderRepository;
import com.project.ecommerce.repository.PaymentRepository;
import com.project.ecommerce.ultil.VNPayUtil;
import com.project.ecommerce.ultil.constant.PaymentMethodEnum;
import com.project.ecommerce.ultil.constant.PaymentStatusEnum;
import com.project.ecommerce.ultil.constant.StatusEnum;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final VNPAYConfig vnpayConfig;

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

    public ResPaymentVNPAYDTO createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnpayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return new ResPaymentVNPAYDTO("ok","success",paymentUrl);

    }


}
