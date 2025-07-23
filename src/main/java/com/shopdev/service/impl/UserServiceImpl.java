package com.shopdev.service.impl;

import com.shopdev.dto.request.UserRequest;
import com.shopdev.dto.response.UserResponse;
import com.shopdev.enums.ErrorCode;
import com.shopdev.enums.ROLE;
import com.shopdev.exception.AppException;
import com.shopdev.mapper.UserMapper;
import com.shopdev.model.UserEntity;
import com.shopdev.repository.UserRepository;
import com.shopdev.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public UserEntity createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        HashSet<String> roles = new HashSet<>();
        roles.add(ROLE.USER.name());

        UserEntity result = userMapper.userEntityToUserRequest(userRequest);
        result.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        result.setRoles(roles);

        return userRepository.save(result);
    }

    @Override
    public UserResponse login(UserRequest userRequest) {
        UserEntity findUser = userRepository.findByEmail(userRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var checkPassword = passwordEncoder.matches(userRequest.getPassword(), findUser.getPassword());

        if (!checkPassword) {
            throw new RuntimeException("Wrong Password");
        }

        return userMapper.userEntityToUserResponse(findUser);
    }
}
