package com.shopdev.service.impl;

import com.shopdev.dto.request.UserRequest;
import com.shopdev.model.UserEntity;
import com.shopdev.repository.UserRepository;
import com.shopdev.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public UserEntity createUser(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(userRequest.getEmail());
//        userEntity.setName(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());

        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }
}
