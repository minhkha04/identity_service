package com.minhkha.backend.config;

import com.minhkha.backend.entity.User;
import com.minhkha.backend.eums.Role;
import com.minhkha.backend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (!userRepository.existsUserByRole(Role.ADMIN)) {
                User user = User.builder()
                        .email("admin@admin")
                        .password(passwordEncoder.encode("12345678"))
                        .fullName("Admin")
                        .role(Role.ADMIN)
                        .createdAt(LocalDateTime.now())
                        .avatarUrl("https://res.cloudinary.com/di6hi1r0g/image/upload/v1749288955/default-avatar_qwb4ru.png")
                        .build();
                userRepository.save(user);
                log.warn("Admin user has been created!");
            }
        };
    }
}
