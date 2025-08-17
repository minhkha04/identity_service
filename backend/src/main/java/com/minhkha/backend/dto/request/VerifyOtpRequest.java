package com.minhkha.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VerifyOtpRequest {
    @Size(max = 6, min = 6, message = "Otp phải có 6 ký tự")
    String otp;
    @Email(message = "Email không đúng định dạng")
    String email;
}
