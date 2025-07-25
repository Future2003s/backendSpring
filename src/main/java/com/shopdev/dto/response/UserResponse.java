package com.shopdev.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse implements Serializable {
    String id;
    String user_fullName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String phone_number;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String user_address;
    String access_token;

    String refresh_token;
    
    Set<String> roles = new HashSet<>();
}
