package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.Enum.OrderStatus;
import com.CeritaBakmiBE.CB.Enum.PaymentStatus;

public interface UpdatePaymentStatus {

    void updatePaymentStatus(long transactionId, PaymentStatus paymentStatus) throws Exception;
}
