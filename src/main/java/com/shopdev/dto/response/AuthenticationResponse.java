package com.shopdev.dto.response;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse implements Serializable {
    String lastName;
    String firstName;
    String fullName;
    String access_token;
    String refresh_token;
    long expires_in; // seconds until access token expiration
}
