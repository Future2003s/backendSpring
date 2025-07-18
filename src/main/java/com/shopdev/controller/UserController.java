package com.shopdev.controller;


import com.shopdev.dto.request.UserRequest;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.dto.response.UserResponse;
import com.shopdev.model.UserEntity;
import com.shopdev.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/createUser")
    public UserEntity createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PostMapping("/login")
    public ResponseData<UserResponse> login(@RequestBody @Valid UserRequest request) {
        return new ResponseData<>(HttpStatus.OK, "Login SuccessFully", userService.login(request));
    }
}
