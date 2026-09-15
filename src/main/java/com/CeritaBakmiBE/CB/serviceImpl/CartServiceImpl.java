package com.CeritaBakmiBE.CB.serviceImpl;

import com.CeritaBakmiBE.CB.repository.CartItemRepository;
import com.CeritaBakmiBE.CB.repository.CartRepository;
import com.CeritaBakmiBE.CB.request.CartRequest;
import com.CeritaBakmiBE.CB.response.CartResponse;
import com.CeritaBakmiBE.CB.service.CartService;

import java.io.IOException;
import java.util.List;

public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartResponse addCart(CartRequest cartRequest) throws IOException {
        return null;
    }

    @Override
    public void removeFromCart(long id) {

    }

    @Override
    public List<CartResponse> getAllCart() {
        return List.of();
    }

    @Override
    public void updateQuantity(long id, int quantity) {

    }
}
