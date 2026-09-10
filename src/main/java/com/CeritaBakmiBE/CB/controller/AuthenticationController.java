package com.CeritaBakmiBE.CB.controller;


import com.CeritaBakmiBE.CB.request.UserRequest;
import com.CeritaBakmiBE.CB.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name ="Authentication REST API Endpoints", description = "Operations register and login")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Register a user", description = "Create a new user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody UserRequest userRequest) throws Exception{
        authenticationService.register(userRequest);
    }
}
