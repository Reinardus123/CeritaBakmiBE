package com.CeritaBakmiBE.CB.controller;

import com.CeritaBakmiBE.CB.request.CheckoutRequest;
import com.CeritaBakmiBE.CB.response.CheckoutResponse;
import com.CeritaBakmiBE.CB.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/checkout")
@Tag(name = "Checkout Endpoint", description = "Checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Operation(summary = "Checkout menu", description = "Checkout menu")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/item")
    public CheckoutResponse checkout(@Valid CheckoutRequest checkoutRequest){
        return checkoutService.checkout(checkoutRequest);
    }
}
