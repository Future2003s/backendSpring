package com.shopdev.service;

import com.shopdev.dto.request.UserRequest;
import com.shopdev.model.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity createUser(UserRequest userRequest);

    List<UserEntity> findAllUsers();
}
