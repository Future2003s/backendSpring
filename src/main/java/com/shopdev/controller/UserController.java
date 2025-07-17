package com.shopdev.controller;


import com.shopdev.dto.request.UserRequest;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.exception.ErrorHandler;
import com.shopdev.model.UserEntity;
import com.shopdev.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/user")
    public UserEntity createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/findAllUser")
    public ResponseData<List<UserEntity>> findAllUsers() {
        List<UserEntity> result = userService.findAllUsers();
        if (result == null) {
            throw new ErrorHandler("Error result");
        }
        return new ResponseData<>(HttpStatus.OK, "Get All User SuccessFully", result);
    }
}
