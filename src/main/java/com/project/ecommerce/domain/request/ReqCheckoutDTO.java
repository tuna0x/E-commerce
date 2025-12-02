package com.project.ecommerce.domain.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReqCheckoutDTO {
    private String shippingAddress;
    private List<Long> cartItemIDs;
}
