package com.shopdev.model;


import com.shopdev.enums.ROLE;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@Table(name = "usr_account")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends AbstractEntity {

    @Column(name = "usr_first_name", nullable = false, length = 100)
    String firstName;

    @Column(name = "usr_last_name", nullable = false, length = 100)
    String lastName;

    @Column(name = "usr_full_name")
    String fullName;

    @Column(name = "usr_email", nullable = false, length = 100)
    String email;

    @Column(name = "usr_password", nullable = false, length = 100)
    String password;

    @Column(name = "usr_phone_number", length = 20)
    String phoneNumber;

    @Column(name = "usr_address", length = 200)
    String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "usr_role", length = 20)
    ROLE role;


}
