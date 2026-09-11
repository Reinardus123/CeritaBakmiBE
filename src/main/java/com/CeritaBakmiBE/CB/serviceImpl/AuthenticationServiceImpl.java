package com.CeritaBakmiBE.CB.serviceImpl;

import com.CeritaBakmiBE.CB.entity.User;
import com.CeritaBakmiBE.CB.repository.UserRepository;
import com.CeritaBakmiBE.CB.request.AuthRequest;
import com.CeritaBakmiBE.CB.request.UserRequest;
import com.CeritaBakmiBE.CB.response.AuthResponse;
import com.CeritaBakmiBE.CB.service.AuthenticationService;
import com.CeritaBakmiBE.CB.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void register(UserRequest input) throws Exception {
        if(isEmailTaken(input.getEmail())){
            throw new Exception("Email sudah digunakan");
        }
        User user = buildNewUser(input);
        userRepository.save(user);
    }

    private boolean isEmailTaken(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    private User buildNewUser(UserRequest input){
        User user = new User();
        user.setEmail(input.getEmail());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole("CUSTOMER");
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword())
        );
        User user = userRepository.findByUsername(input.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Username atau password salah!"));

        String jwtToken = jwtService.generateToken(new HashMap<>(), user);
        return new AuthResponse(jwtToken);
    }




}

