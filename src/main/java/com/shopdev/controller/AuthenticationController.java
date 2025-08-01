package com.shopdev.controller;


import com.nimbusds.jose.JOSEException;
import com.shopdev.dto.request.AuthenticationRequest;
import com.shopdev.dto.request.IntrospectRequest;
import com.shopdev.dto.request.UserRequest;
import com.shopdev.dto.response.AuthenticationResponse;
import com.shopdev.dto.response.IntrospectResponse;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.model.UserEntity;
import com.shopdev.service.AuthenticationService;
import com.shopdev.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    UserService userService;
    AuthenticationService authenticationService;

    @PostMapping("/createUser")
    public ResponseData<UserEntity> createUser(@RequestBody @Valid UserRequest userRequest) {
        return new ResponseData<>(HttpStatus.CREATED, "Create User SuccessFully", userService.createUser(userRequest));
    }

    @PostMapping("/login")
    public ResponseData<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        return new ResponseData<>(
                HttpStatus.OK
                , "Login SuccessFully"
                , authenticationService.login(request));
    }

    @PostMapping("/introspect")
    public ResponseData<IntrospectResponse> login(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        return new ResponseData<>(HttpStatus.OK, "Verify Token SuccessFully", authenticationService.introspect(request));
    }

}
