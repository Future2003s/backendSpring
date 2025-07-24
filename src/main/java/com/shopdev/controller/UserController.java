package com.shopdev.controller;


import com.shopdev.dto.response.ResponseData;
import com.shopdev.enums.ErrorCode;
import com.shopdev.exception.AppException;
import com.shopdev.model.UserEntity;
import com.shopdev.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableMethodSecurity
public class UserController {
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseData<List<UserEntity>> users() {
        /**
         * Lấy được ROLE JWT SCOPE
         * Authentication -> SecurityContextHolder.getContext().getAuthentication()
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        return new ResponseData<>(HttpStatus.OK, "Get All Users SuccessFully", userRepository.findAll());
    }


    @PostAuthorize("hasRole('ADMIN')")
    @GetMapping("/info-user/{userId}")
    public ResponseData<UserEntity> userInfo(@PathVariable(name = "userId") Long userId) {
        String id = userId.toString();
        return new ResponseData<>(HttpStatus.OK, "Get Information SuccessFully",
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED))
        );
    }
}
