package com.shopdev.mapper;

import com.shopdev.dto.request.UserRequest;
import com.shopdev.dto.response.UserResponse;
import com.shopdev.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity userEntityToUserRequest(UserRequest userEntity);


    @Mapping(source = "fullName", target = "user_fullName")
    @Mapping(source = "email", target = "user_email")
    UserResponse userEntityToUserResponse(UserEntity userEntity);
}
