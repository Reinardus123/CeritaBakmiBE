package com.CeritaBakmiBE.CB.service;

import com.CeritaBakmiBE.CB.request.UserRequest;

public interface AuthenticationService {

    void register(UserRequest input) throws Exception;

}
