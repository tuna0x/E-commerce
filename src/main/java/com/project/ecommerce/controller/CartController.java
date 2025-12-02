package com.project.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.domain.Cart;
import com.project.ecommerce.domain.User;
import com.project.ecommerce.domain.response.cart.ResAddToCart;
import com.project.ecommerce.domain.response.cart.ResGetCart;
import com.project.ecommerce.service.CartService;
import com.project.ecommerce.service.UserService;
import com.project.ecommerce.ultil.SecurityUtil;
import com.project.ecommerce.ultil.annotation.APIMessage;
import com.project.ecommerce.ultil.error.IdInvalidException;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/cart")
    @APIMessage("Add to cart successfully")
    public ResponseEntity<ResAddToCart> addToCart(
    @RequestParam long productId,
    @RequestParam(defaultValue = "1") int quantity) throws IdInvalidException {
        Cart cart=this.cartService.addToCart(productId, quantity);
        return ResponseEntity.ok().body(this.cartService.convertToResAddToCart(cart));
    }

    @GetMapping("/cart")
    @APIMessage("Get cart successfully")
    public ResponseEntity<ResGetCart> getCart() {
        Cart cart =this.cartService.getOrCreateCart();
        return ResponseEntity.ok().body(this.cartService.convertToResGetCart(cart));
    }

    @PutMapping("/cart")
    @APIMessage("Update cart item quantity successfully")
    public ResponseEntity<ResAddToCart> updateQuantity(
    @RequestParam long itemId,
    @RequestParam int quantity) {
        Cart cart=this.cartService.updateQuantity(quantity, itemId);
        return ResponseEntity.ok().body(this.cartService.convertToResAddToCart(cart));
    }

    @DeleteMapping("/cart/{id}")
    @APIMessage("Remove item from cart successfully")
    public ResponseEntity<Void> updateQuantity(
    @PathVariable ("id") long id) {
        this.cartService.removeItem(id);
        return ResponseEntity.ok().body(null);
    }


}
