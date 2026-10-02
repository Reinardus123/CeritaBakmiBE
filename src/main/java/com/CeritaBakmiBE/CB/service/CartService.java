package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.request.CartRequest;
import com.CeritaBakmiBE.CB.request.UpdateOrderStatusRequest;
import com.CeritaBakmiBE.CB.request.UpdateQuantityRequest;
import com.CeritaBakmiBE.CB.response.CartResponse;

import java.io.IOException;
import java.util.List;

public interface CartService {

    CartResponse addCart(CartRequest cartRequest) throws IOException;
    void removeFromCart(long id);
    List<CartResponse>getAllCart();
    CartResponse updateQuantity(long cartMenuId ,UpdateQuantityRequest request);



}
