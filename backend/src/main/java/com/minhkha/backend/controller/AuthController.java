package com.minhkha.backend.controller;

import com.minhkha.backend.dto.request.*;
import com.minhkha.backend.dto.response.ApiResponse;
import com.minhkha.backend.dto.response.AuthenticationResponse;
import com.minhkha.backend.dto.response.IntrospectResponse;
import com.minhkha.backend.eums.AuthProvider;
import com.minhkha.backend.eums.ResendOtpType;
import com.minhkha.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(
            @RequestParam AuthProvider provider,
            @RequestBody AuthRequest request
            ) {
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authService.login(request, provider))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<Void> register(
            @RequestBody @Valid UserCreateRequest request
    ) {
        authService.register(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(
            @RequestBody @Valid IntrospectRequest request
    ) {
        return ApiResponse.<IntrospectResponse>builder()
                .data(authService.introspect(request))
                .build();
    }

    @PostMapping("/send-otp")
    public ApiResponse<Void> resendOtp(
            @RequestBody @Valid ResendOtpRequest request,
            @RequestParam ResendOtpType type
    ) {
        authService.resendOtpRequest(request, type);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/verify-otp")
    public ApiResponse<AuthenticationResponse> verifyOtp(@RequestBody @Valid VerifyOtpRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authService.verifyOtp(request))
                .build();
    }

    @PostMapping("/reset-password")
    public ApiResponse<AuthenticationResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authService.resetPassword(request))
                .build();
    }

}
