package com.project.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.domain.Order;
import com.project.ecommerce.domain.Payment;
import com.project.ecommerce.domain.request.ReqCheckoutDTO;
import com.project.ecommerce.domain.response.order.ResGetOderDTO;
import com.project.ecommerce.service.OrderService;
import com.project.ecommerce.service.PaymentService;
import com.project.ecommerce.ultil.annotation.APIMessage;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping("/order/checkout")
    @APIMessage("checkout")
    public ResponseEntity<Payment> checkout(@RequestBody ReqCheckoutDTO reqCheckoutDTO) {
        Order order=this.orderService.createOder(reqCheckoutDTO.getShippingAddress(), reqCheckoutDTO.getCartItemIDs());
        Payment payment = this.paymentService.createCODPayment(order.getId());
        return ResponseEntity.ok().body(payment);
    }

    @GetMapping("/order/{id}")
    @APIMessage("get order by id")
    public ResponseEntity<ResGetOderDTO> getOrder(@PathVariable("id") Long id) {
        Order order=this.orderService.getOrder(id);
        return ResponseEntity.ok().body(this.orderService.convertToResGetOderDTO(order));
    }

}
