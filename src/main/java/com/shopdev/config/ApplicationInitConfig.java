package com.shopdev.config;


import com.shopdev.enums.ROLE;
import com.shopdev.model.UserEntity;
import com.shopdev.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initApplicationRunner(UserRepository userRepository) {
        return (args) -> {
            if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
                HashSet<String> roles = new HashSet<>();
                roles.add(ROLE.ADMIN.name());
                UserEntity userEntity = UserEntity.builder()
                        .fullName("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin@123"))
                        .roles(roles)
                        .build();
                userRepository.save(userEntity);
                log.info("ADMIN ROLE WAS REGISTERED WITH: {} {}", "admin@gmail.com", "admin@123");
            }
        };
    }
}
