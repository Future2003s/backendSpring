package com.shopdev.service.impl;

import com.shopdev.dto.request.AuthenticationRequest;
import com.shopdev.dto.request.IntrospectRequest;
import com.shopdev.dto.response.AuthenticationResponse;
import com.shopdev.dto.response.IntrospectResponse;
import com.shopdev.service.AuthenticationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        return null;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        
        return null;
    }
}
