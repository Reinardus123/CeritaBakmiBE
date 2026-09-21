package com.CeritaBakmiBE.CB.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDetailResponse {

    private String menuTitle;
    private double price;
    private int quantity;
    private double subtotal;
}
