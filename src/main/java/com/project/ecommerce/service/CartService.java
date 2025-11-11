package com.project.ecommerce.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.ecommerce.domain.Cart;
import com.project.ecommerce.domain.CartItem;
import com.project.ecommerce.domain.Product;
import com.project.ecommerce.domain.User;
import com.project.ecommerce.repository.CartItemRepository;
import com.project.ecommerce.repository.CartRepository;
import com.project.ecommerce.repository.UserRepository;
import com.project.ecommerce.ultil.SecurityUtil;
import com.project.ecommerce.ultil.error.IdInvalidException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;


    // public Cart createCart(long userId){
    //     Cart cart=new Cart();
    //     String email=SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
    //     User user = this.userRepository.findByEmail(email);
    //     cart.setId(userId);
    //     cart.setUser(user);
    //     return this.cartRepository.save(cart);
    // }

    public Cart getOrCreateCart(){
        String email=this.securityUtil.getCurrentUserLogin().isPresent() ? this.securityUtil.getCurrentUserLogin().get() : null;
        User user= this.userRepository.findByEmail(email);
        Cart cart=this.cartRepository.findByUser(user);
        if (cart==null) {
            Cart cur= new Cart();
            cur.setUser(user);
            cart=this.cartRepository.save(cur);
        }
        return cart;
    }


    public Cart addToCart(Long productId, int quantity) throws IdInvalidException{
        Cart cart=this.getOrCreateCart();

        Product product=this.productService.handleGetById(productId);
        if (product==null) {
            throw new IdInvalidException("product not found");
        }

        CartItem cartItem =this.cartItemRepository.findByCartIdAndProductId(cart.getId(),productId);
        if (cartItem==null) {
            CartItem newItem=new CartItem();
            double price=product.getOriginalPrice();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(price);
            newItem.setTotalPrice(quantity * price);
        }else{
            CartItem item=cartItem;
            double price=product.getOriginalPrice();
            item.setQuantity(quantity);
            item.setUnitPrice(price);
            item.setTotalPrice(quantity * price);
        }

        return cart;
    }

    public Cart updateQuantity(int quantity, long itemId){
       Optional<CartItem> cartItem= this.cartItemRepository.findById(itemId);
       CartItem item = cartItem.isPresent() ? cartItem.get() : null;

       if (quantity<=0) {
        this.cartItemRepository.delete(item);
       }else{
        item.setQuantity(quantity);
       }

       return item.getCart();
    }

    public void removeItem(Long itemId){
        Optional<CartItem> cartItem= this.cartItemRepository.findById(itemId);
       CartItem item = cartItem.isPresent() ? cartItem.get() : null;
       Cart cart =item.getCart();
       cart.getItems().remove(item);
       this.cartItemRepository.delete(item);

    }


}
