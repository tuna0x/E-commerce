package com.project.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.ecommerce.domain.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>,JpaSpecificationExecutor<CartItem>{
    CartItem findByCartIdAndProductId(long cartId, long productId);
}
