package com.project.ecommerce.domain;

import java.time.Instant;

import com.project.ecommerce.ultil.constant.GenderEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is not blank")
    private String name;
    @NotBlank(message = "Email is not blank")
    private String email;
    private String password;
    private String address;
    private int age;
    private GenderEnum gender;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updateBy;
}
