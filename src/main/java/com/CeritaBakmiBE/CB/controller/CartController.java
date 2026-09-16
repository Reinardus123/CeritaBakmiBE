package com.CeritaBakmiBE.CB.controller;

import com.CeritaBakmiBE.CB.request.CartRequest;
import com.CeritaBakmiBE.CB.response.CartResponse;
import com.CeritaBakmiBE.CB.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cart")
@Tag(name = "Create Cart for customer", description = "Operation for cart")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Create cart", description = "add cart")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public CartResponse addCart(@Valid CartRequest cartRequest) throws IOException {
        return cartService.addCart(cartRequest);
    }

    @Operation(summary = "Get All cart", description = "get all cart from user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getCart")
    public List<CartResponse> getCart(){
        return cartService.getAllCart();
    }

    @Operation(summary = "Delete cart item from cart", description = "delete item from cart")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@Valid @PathVariable long id){
        cartService.removeFromCart(id);
        return ResponseEntity.noContent().build();
    }
}
