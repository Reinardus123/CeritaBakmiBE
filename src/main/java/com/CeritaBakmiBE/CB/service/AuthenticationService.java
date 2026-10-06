package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.request.AuthRequest;
import com.CeritaBakmiBE.CB.request.CustomerRequest;
import com.CeritaBakmiBE.CB.request.UserRequest;
import com.CeritaBakmiBE.CB.response.AuthResponse;
import com.CeritaBakmiBE.CB.response.CustomerResponse;

public interface AuthenticationService {

    void register(UserRequest input) throws Exception;
    AuthResponse login(AuthRequest input);
    CustomerResponse guestUser(CustomerRequest request);
}
