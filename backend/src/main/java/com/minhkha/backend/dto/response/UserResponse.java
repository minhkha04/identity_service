package com.minhkha.backend.dto.response;

import com.minhkha.backend.eums.Role;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class UserResponse {

    String id;
    String email;
    String fullName;
    Role role;
    Date birthDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
