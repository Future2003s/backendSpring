package com.shopdev.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.shopdev.enums.ROLE;
import jakarta.persistence.*;
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
    @Column(name = "usr_full_name")
    String fullName;

    @Column(name = "usr_email", nullable = false, length = 100, unique = true)
    String email;

    @Column(name = "usr_password", nullable = false, length = 100)
    String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "usr_phone_number", length = 20)
    String phoneNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "usr_address", length = 200)
    String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Enumerated(EnumType.STRING)
    @Column(name = "usr_role", length = 20)
    ROLE role;

    Set<String> roles = new HashSet<>();
}
