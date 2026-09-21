package com.CeritaBakmiBE.CB.controller;

import com.CeritaBakmiBE.CB.request.CheckoutRequest;
import com.CeritaBakmiBE.CB.response.CheckoutResponse;
import com.CeritaBakmiBE.CB.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "Get All Menu By User", description = "get All Menu")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/getAll")
    public List<CheckoutResponse> getCheckoutByUser() throws Exception{
        return checkoutService.getTransactionByUser();
    }

}
