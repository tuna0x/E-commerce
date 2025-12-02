package com.project.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ecommerce.domain.OrderItem;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
