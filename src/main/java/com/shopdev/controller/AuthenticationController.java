package com.shopdev.controller;


import com.nimbusds.jose.JOSEException;
import com.shopdev.dto.request.AuthenticationRequest;
import com.shopdev.dto.request.IntrospectRequest;
import com.shopdev.dto.request.UserRequest;
import com.shopdev.dto.response.AuthenticationResponse;
import com.shopdev.dto.response.IntrospectResponse;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.model.UserEntity;
import com.shopdev.repository.UserRepository;
import com.shopdev.service.AuthenticationService;
import com.shopdev.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    UserService userService;
    AuthenticationService authenticationService;
    UserRepository userRepository;

    @PostMapping("/createUser")
    public ResponseData<UserEntity> createUser(@RequestBody @Valid UserRequest userRequest) {
        return new ResponseData<>(HttpStatus.CREATED, "Create User SuccessFully", userService.createUser(userRequest));
    }

    @PostMapping("/login")
    public ResponseData<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return new ResponseData<>(HttpStatus.OK, "Login SuccessFully", authenticationResponse);
    }

    @PostMapping("/introspect")
    public ResponseData<IntrospectResponse> login(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(request);
        return new ResponseData<>(HttpStatus.OK, "Verify Token SuccessFully", result);
    }

    @GetMapping("/users")
    public ResponseData<List<UserEntity>> users() {
        return new ResponseData<>(HttpStatus.OK, "Get All Users SuccessFully", userRepository.findAll());
    }
}
