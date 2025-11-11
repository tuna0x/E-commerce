package com.project.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.domain.Cart;
import com.project.ecommerce.domain.User;
import com.project.ecommerce.service.CartService;
import com.project.ecommerce.service.UserService;
import com.project.ecommerce.ultil.SecurityUtil;
import com.project.ecommerce.ultil.error.IdInvalidException;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private final SecurityUtil securityUtil;
    private final UserService userService;

    // @PostMapping("/carts/{userId}/{productId}")
    // public ResponseEntity<Cart> addToCart(
    // @PathVariable long userId,
    // @PathVariable long productId,
    // @RequestParam(defaultValue = "1") int quantity) throws IdInvalidException {
    //     return ResponseEntity.ok().body(this.cartService.addToCart(userId,productId, quantity));
    // }

    @GetMapping("/cart")
    public ResponseEntity<Cart> getCart() {
        return ResponseEntity.ok().body(this.cartService.getOrCreateCart());
    }


}
