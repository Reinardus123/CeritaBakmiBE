package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.request.AuthRequest;
import com.CeritaBakmiBE.CB.request.UserRequest;
import com.CeritaBakmiBE.CB.response.AuthResponse;

public interface AuthenticationService {

    void register(UserRequest input) throws Exception;
    AuthResponse login(AuthRequest input);
}
