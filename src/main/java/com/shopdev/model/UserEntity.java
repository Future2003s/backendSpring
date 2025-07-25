package com.shopdev.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "usr_account")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends AbstractEntity {
    @Column(name = "usr_first_name")
    String firstName;

    @Column(name = "usr_last_name")
    String lastName;

    @Column(name = "usr_full_name")
    String fullName;

    @Column(name = "usr_email", nullable = false, length = 100, unique = true)
    String email;

    @Column(name = "usr_password", nullable = false, length = 100)
    String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "usr_phone_number", length = 20)
    String phone_number;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "usr_address", length = 200)
    String address;

    Set<String> roles = new HashSet<>();
}
