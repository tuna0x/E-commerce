package com.project.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem {
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private String productName;
    private int quantity;
    private Double price;
    private Double subTotal;
}