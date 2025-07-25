package com.shopdev.mapper;

import com.shopdev.dto.request.UserRequest;
import com.shopdev.model.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity userEntityToUserRequest(UserRequest userEntity);


//    @Mapping(source = "user_fullName", target = "usr_full_name")
//    @Mapping(source = "usr_email", target = "user_email")
//    UserResponse userEntityToUserResponse(UserEntity userEntity);
}
