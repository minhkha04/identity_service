package com.minhkha.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResetPasswordRequest {

    @Email(message = "Email không đúng định dạng")
    String email;
    @Size(min = 6, max = 255, message = "Mật khẩu phải từ 6 đến 255 ký tự")
    @NotBlank(message = "Mật khẩu không được để trống")
    String password;
    @NotBlank(message = "Mật khẩu không được để trống")
    String otp;
}
