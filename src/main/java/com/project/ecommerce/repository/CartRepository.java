package com.project.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.ecommerce.domain.Cart;
import com.project.ecommerce.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long>,JpaSpecificationExecutor<Cart>{
    Cart findByUser(User user);
}
