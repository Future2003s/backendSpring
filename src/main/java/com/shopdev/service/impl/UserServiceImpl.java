package com.shopdev.service.impl;

import com.shopdev.dto.request.UserRequest;
import com.shopdev.dto.response.UserResponse;
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


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserEntity createUser(UserRequest userRequest) {
        boolean checkUser = userRepository.existsByEmail(userRequest.getEmail());
        if (checkUser) {
            throw new RuntimeException("User with email " + userRequest.getEmail() + " already exists");
        }


        log.info("INFO USER :{}", checkUser);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String hashPassword = passwordEncoder.encode(userRequest.getPassword());

//        UserEntity userEntity = UserEntity.builder()
//                .fullName(userRequest.getFullName())
//                .password(hashPassword)
//                .email(userRequest.getEmail())
//                .build();

        UserEntity result = userMapper.userEntityToUserRequest(userRequest);
        result.setPassword(hashPassword);

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
