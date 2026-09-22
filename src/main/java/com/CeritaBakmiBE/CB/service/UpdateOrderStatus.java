package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.Enum.OrderStatus;

public interface UpdateOrderStatus {

    void updateOrderStatus(long transactionId, OrderStatus orderStatus) throws Exception;
}
