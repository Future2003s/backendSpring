package com.shopdev.service;

import com.shopdev.dto.request.UpdateMeRequest;
import com.shopdev.dto.request.UserRequest;
import com.shopdev.dto.response.UserResponse;
import com.shopdev.model.UserEntity;

public interface UserService {
    UserEntity createUser(UserRequest userRequest);

    UserResponse login(UserRequest userRequest);

    UserEntity updateMe(String email, UpdateMeRequest request);
}
