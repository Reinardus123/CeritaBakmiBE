package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.request.CheckoutRequest;
import com.CeritaBakmiBE.CB.response.BranchResponse;
import com.CeritaBakmiBE.CB.response.CheckoutResponse;

import java.util.List;

public interface CheckoutService {

    CheckoutResponse checkout(CheckoutRequest checkoutRequest);
    List<CheckoutResponse> getAllTransaction();
    List<CheckoutResponse> getTransactionByUser() throws Exception;


}
