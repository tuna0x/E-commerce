package com.project.ecommerce.domain.response.cart;

import java.util.List;

import com.project.ecommerce.domain.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResAddToCart {
    private long id;
    private UserInner user;
    private List<CartItemInner> item;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UserInner {
        private long id;
        private String name;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class  CartItemInner {
    private long id;
    private ProductIner product;
    private double unitPrice;
    private int quantity;
    private double totalPrice;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class ProductIner {
            private long id;
            private String name;
            private String image;
        }
    }
}
