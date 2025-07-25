package com.shopdev.service;


import com.nimbusds.jose.JOSEException;
import com.shopdev.dto.request.AuthenticationRequest;
import com.shopdev.dto.request.IntrospectRequest;
import com.shopdev.dto.response.AuthenticationResponse;
import com.shopdev.dto.response.IntrospectResponse;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    AuthenticationResponse login(AuthenticationRequest request);
}
