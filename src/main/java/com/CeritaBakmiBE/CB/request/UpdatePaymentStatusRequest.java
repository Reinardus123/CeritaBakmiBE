package com.CeritaBakmiBE.CB.request;

import com.CeritaBakmiBE.CB.Enum.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePaymentStatusRequest {

    private PaymentStatus paymentStatus;

}
