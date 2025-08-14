package com.minhkha.backend.controller;

import com.minhkha.backend.dto.request.AuthRequest;
import com.minhkha.backend.dto.request.IntrospectRequest;
import com.minhkha.backend.dto.request.UserCreateRequest;
import com.minhkha.backend.dto.response.ApiResponse;
import com.minhkha.backend.dto.response.AuthenticationResponse;
import com.minhkha.backend.dto.response.IntrospectResponse;
import com.minhkha.backend.eums.AuthProvider;
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
    public ApiResponse<AuthenticationResponse> register(
            @RequestBody @Valid UserCreateRequest request
    ) {
        return ApiResponse.<AuthenticationResponse>builder()
                .data(authService.register(request))
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

}
