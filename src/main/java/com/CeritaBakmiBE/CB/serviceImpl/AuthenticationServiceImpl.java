package com.CeritaBakmiBE.CB.serviceImpl;

import com.CeritaBakmiBE.CB.entity.User;
import com.CeritaBakmiBE.CB.repository.UserRepository;
import com.CeritaBakmiBE.CB.request.AuthRequest;
import com.CeritaBakmiBE.CB.request.CustomerRequest;
import com.CeritaBakmiBE.CB.request.UserRequest;
import com.CeritaBakmiBE.CB.response.AuthResponse;
import com.CeritaBakmiBE.CB.response.CustomerResponse;
import com.CeritaBakmiBE.CB.service.AuthenticationService;
import com.CeritaBakmiBE.CB.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


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
        user.setRole("ADMIN");
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

    @Override
    public CustomerResponse guestUser(CustomerRequest request) {

        Optional<User> UserByPhoneNumber = userRepository.findByPhoneNumber(request.getPhoneNumber());
        if(UserByPhoneNumber.isPresent()){
            if(UserByPhoneNumber.get().getRole().equals("CUSTOMER")){
                User user = UserByPhoneNumber.get();


                Map<String, Object> claims = new HashMap<>();
                claims.put("role",user.getRole());
                String jwtToken = jwtService.generateToken(claims, user);

                return new CustomerResponse(jwtToken);

            } else{
                throw new RuntimeException("Tidak Terautorisasi");
            }
        } else{

            User user = buildGuestCustomer(request);
            userRepository.save(user);

            Map<String, Object> claims = new HashMap<>();
            claims.put("role",user.getRole());
            String jwtToken = jwtService.generateToken(claims, user);

            return new CustomerResponse(jwtToken);
        }
    }

    private User buildGuestCustomer(CustomerRequest request){

        String randomPassword = UUID.randomUUID().toString();

        User user = new User();
        user.setCustName(request.getName());
        user.setUsername(request.getPhoneNumber());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole("CUSTOMER");
        user.setEmail(request.getPhoneNumber() + "@gmail.com");
        user.setPassword(passwordEncoder.encode(randomPassword));
        return user;
    }


}

