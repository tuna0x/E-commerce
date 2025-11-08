package com.project.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ecommerce.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    boolean existsByEmail(String email);
    User findByEmail(String email);
}
