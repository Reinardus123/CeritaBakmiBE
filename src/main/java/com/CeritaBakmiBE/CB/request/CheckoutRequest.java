package com.CeritaBakmiBE.CB.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class CheckoutRequest {

    private Long branchId;

    private String deliveryAddress;

}
