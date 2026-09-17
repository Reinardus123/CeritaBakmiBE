package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.request.CheckoutRequest;
import com.CeritaBakmiBE.CB.response.BranchResponse;
import com.CeritaBakmiBE.CB.response.CheckoutResponse;

public interface CheckoutService {

    CheckoutResponse checkout(CheckoutRequest checkoutRequest);


}
