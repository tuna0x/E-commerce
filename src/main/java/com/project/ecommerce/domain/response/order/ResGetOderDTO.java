package com.project.ecommerce.domain.response.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.ecommerce.domain.OrderItem;
import com.project.ecommerce.domain.Payment;
import com.project.ecommerce.domain.User;
import com.project.ecommerce.ultil.constant.PaymentStatusEnum;
import com.project.ecommerce.ultil.constant.StatusEnum;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResGetOderDTO {
    private long id;

    private UserInner user;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    private String ShippingAddress;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInner {
    private Long id;
    private String name;
    private String email;
    }
}
