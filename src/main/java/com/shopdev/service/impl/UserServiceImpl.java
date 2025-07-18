package com.shopdev.service.impl;

import com.shopdev.dto.request.UserRequest;
import com.shopdev.dto.response.UserResponse;
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

    @Override
    public UserEntity createUser(UserRequest userRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String hashPassword = passwordEncoder.encode(userRequest.getPassword());

        UserEntity userEntity = UserEntity.builder()
                .fullName(userRequest.getFullName())
                .password(hashPassword)
                .email(userRequest.getEmail())
                .build();

        return userRepository.save(userEntity);
    }

    @Override
    public UserResponse login(UserRequest userRequest) {
        UserEntity findUser = userRepository.findByEmail(userRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var checkPassword = passwordEncoder.matches(userRequest.getPassword(), findUser.getPassword());

        if (!checkPassword) {
            throw new RuntimeException("Wrong Password");
        }

        log.info("Check Password {}", checkPassword);


        return UserResponse.builder()
                .usr_full_name(findUser.getFullName())
                .usr_email(findUser.getEmail())
                .build();
    }
}
