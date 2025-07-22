package com.shopdev.service;


import com.shopdev.dto.request.AuthenticationRequest;
import com.shopdev.dto.request.IntrospectRequest;
import com.shopdev.dto.response.AuthenticationResponse;
import com.shopdev.dto.response.IntrospectResponse;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
