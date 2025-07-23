package com.shopdev.controller;


import com.shopdev.dto.response.ResponseData;
import com.shopdev.model.UserEntity;
import com.shopdev.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseData<List<UserEntity>> users() {
        return new ResponseData<>(HttpStatus.OK, "Get All Users SuccessFully", userRepository.findAll());
    }
}
