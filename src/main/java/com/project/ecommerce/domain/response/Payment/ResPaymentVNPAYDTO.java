package com.project.ecommerce.domain.response.Payment;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResPaymentVNPAYDTO implements Serializable{
    private String status;
    private String message;
    private String URL;
}
