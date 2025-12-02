package com.project.ecommerce.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.ecommerce.domain.Cart;
import com.project.ecommerce.domain.CartItem;
import com.project.ecommerce.domain.Product;
import com.project.ecommerce.domain.User;
import com.project.ecommerce.domain.response.cart.ResAddToCart;
import com.project.ecommerce.domain.response.cart.ResGetCart;
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

    public CartItem createNewCartItem(Cart cart,Product product, double price, int quantity){
        CartItem cartItem= new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(price);
        cartItem.setTotalPrice(quantity * price);
        return this.cartItemRepository.save(cartItem);
    }

    public CartItem updateCartItem(Cart cart, double price, int quantity){
        CartItem cartItem= new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(price);
        cartItem.setTotalPrice(quantity * price);
        return cartItem;
    }




    public Cart addToCart(Long productId, int quantity) throws IdInvalidException{
        // Lấy cart của user
        Cart cart=this.getOrCreateCart();

        // Kiểm tra xem product tồn tại ko
        Product product=this.productService.handleGetById(productId);
        if (product==null) {
            throw new IdInvalidException("product not found");
        }

        // kiểm tra xem cart item có tồn taji không
        CartItem cartItem =this.cartItemRepository.findByCartIdAndProductId(cart.getId(),productId);
        Double price=product.getDiscountedPrice();
        if (cartItem==null) {
            cartItem = this.createNewCartItem(cart, product, price, quantity);
        }
        cart.getItems().add(cartItem);
        cart=this.cartRepository.save(cart);

        return cart;
    }

    public Cart updateQuantity(int quantity, long itemId){
        Cart cart =this.getOrCreateCart();
       Optional<CartItem> cartItem= this.cartItemRepository.findById(itemId);
       CartItem item = cartItem.isPresent() ? cartItem.get() : null;

       if (quantity<=0) {
        this.cartItemRepository.delete(item);
       }else{
        item.setQuantity(quantity);
        item=this.cartItemRepository.save(item);
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

    public ResGetCart convertToResGetCart(Cart cart){
        ResGetCart res = new ResGetCart();
        ResGetCart.UserInner resUser = new ResGetCart.UserInner();
        res.setId(cart.getId());
        resUser.setId(cart.getUser().getId());
        resUser.setName(cart.getUser().getName());
        res.setUser(resUser);
        List<ResGetCart.CartItemInner> list=cart.getItems().stream().map(
            x->new ResGetCart.CartItemInner(x.getId(),
            new ResGetCart.CartItemInner.ProductIner(x.getProduct().getId(),x.getProduct().getName(),x.getProduct().getImage()),
            x.getUnitPrice(), x.getQuantity(), x.getTotalPrice()))
            .collect(Collectors.toList());
        res.setItem(list);
        return res;
    }

        public ResAddToCart convertToResAddToCart(Cart cart){
        ResAddToCart res = new ResAddToCart();
        ResAddToCart.UserInner resUser = new ResAddToCart.UserInner();
        res.setId(cart.getId());
        resUser.setId(cart.getUser().getId());
        resUser.setName(cart.getUser().getName());
        res.setUser(resUser);
        List<ResAddToCart.CartItemInner> list=cart.getItems().stream().map(
            x->new ResAddToCart.CartItemInner(x.getId(),
            new ResAddToCart.CartItemInner.ProductIner(x.getProduct().getId(),x.getProduct().getName(),x.getProduct().getImage()),
            x.getUnitPrice(), x.getQuantity(), x.getTotalPrice()))
            .collect(Collectors.toList());
        res.setItem(list);
        return res;
    }


}
