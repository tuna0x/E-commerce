package com.project.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
@Table  (name = "payments")
public class Payment {
    private Long id;
    
    @OneToOne(mappedBy = "payment")
    @JsonIgnore
    private Order order;
    private double amount;// số tiền thanh toán
    private String method;// phương thức thanh toán
    private String status;// trạng thái thanh toán
    private String transactionId;// mã giao dịch
    private String paymentDate;// ngày thanh toán
}
