package com.CeritaBakmiBE.CB.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CartResponse {

    private Long cartItemId;
    private Long menuId;
    private String menuTitle;
    private int price;
    private String imageUrl;
    private int quantity;
    private int subtotal;
}
