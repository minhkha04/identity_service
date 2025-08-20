package com.minhkha.backend.entity;

import com.minhkha.backend.eums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true, nullable = false)
    String email;

    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(length = 50, nullable = false)
    String fullName;

    @Column(length = 2048)
    String avatarUrl;

    @Column(nullable = false)
    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    LocalDate birthDate;
}
