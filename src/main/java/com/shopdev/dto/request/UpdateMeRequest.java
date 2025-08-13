package com.shopdev.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMeRequest implements Serializable {
    String firstName;
    String lastName;
    String fullName;
    String phone_number;
    String address;
}


