package com.minhkha.backend.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResendOtpRequest {
    @Email(message = "Email không đúng định dạng")
    String email;
}
