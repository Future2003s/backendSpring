package com.shopdev.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse implements Serializable {
    String user_fullName;
    String user_email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String phone_number;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String user_address;
}
