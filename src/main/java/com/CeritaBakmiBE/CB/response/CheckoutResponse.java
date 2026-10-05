package com.CeritaBakmiBE.CB.response;

import com.CeritaBakmiBE.CB.Enum.OrderStatus;
import com.CeritaBakmiBE.CB.Enum.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CheckoutResponse {

    private Long transactionId;

    private String BranchName;

    private String deliveryAddress;

    private int subtotal;

    private double totalAmount;

    private PaymentStatus paymentStatus;

    private OrderStatus orderStatus;

    private String whatsappUrl;

    private LocalDateTime createdAt;

    private String username;

    private List<TransactionDetailResponse> details;
}
