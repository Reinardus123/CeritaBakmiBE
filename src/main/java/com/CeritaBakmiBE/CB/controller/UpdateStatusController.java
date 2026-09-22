package com.CeritaBakmiBE.CB.controller;

import com.CeritaBakmiBE.CB.request.UpdateOrderStatusRequest;
import com.CeritaBakmiBE.CB.request.UpdatePaymentStatusRequest;
import com.CeritaBakmiBE.CB.service.UpdateOrderStatus;
import com.CeritaBakmiBE.CB.service.UpdatePaymentStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/updateStatus")
public class UpdateStatusController {

    private final UpdateOrderStatus updateOrderStatus;
    private final UpdatePaymentStatus updatePaymentStatus;

    @Operation(summary = "Update Payment Status", description = "Update Payment Status")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/updatePaymentStatus")
    public ResponseEntity<String> updatePaymentStatus(@Valid @PathVariable long id, @RequestBody UpdatePaymentStatusRequest updatePaymentStatusRequest) throws Exception{
        updatePaymentStatus.updatePaymentStatus(id, updatePaymentStatusRequest.getPaymentStatus());
        return ResponseEntity.ok("Payment status diubah");
    }

    @Operation(summary = "Update Payment Status", description = "Update Payment Status")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/updateOrderStatus")
    public ResponseEntity<String> updateOrderStatus(@Valid @PathVariable long id, @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest) throws Exception{
        updateOrderStatus.updateOrderStatus(id, updateOrderStatusRequest.getOrderStatus());
        return ResponseEntity.ok("Order status diubah");
    }


}
