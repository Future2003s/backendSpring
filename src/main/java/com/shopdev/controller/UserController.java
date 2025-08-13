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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserRepository userRepository;
    com.shopdev.service.UserService userService;

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
    public ResponseData<UserEntity> userInfo(@PathVariable(name = "userId") String userId) {
        return new ResponseData<>(HttpStatus.OK, "Get Information SuccessFully",
                userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED))
        );
    }

    @GetMapping("/me")
    public ResponseData<UserEntity> me() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return new ResponseData<>(HttpStatus.OK, "Get Me Successfully", user);
    }

    @PutMapping("/me")
    public ResponseData<UserEntity> updateMe(@RequestBody com.shopdev.dto.request.UpdateMeRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity updated = userService.updateMe(email, request);
        return new ResponseData<>(HttpStatus.OK, "Update Me Successfully", updated);
    }
}
