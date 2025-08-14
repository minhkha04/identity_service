package com.minhkha.backend.service;

import com.minhkha.backend.dto.request.AuthRequest;
import com.minhkha.backend.dto.request.IntrospectRequest;
import com.minhkha.backend.dto.request.UserCreateRequest;
import com.minhkha.backend.dto.response.AuthenticationResponse;
import com.minhkha.backend.dto.response.IntrospectResponse;
import com.minhkha.backend.entity.User;
import com.minhkha.backend.eums.AuthProvider;
import com.minhkha.backend.eums.Role;
import com.minhkha.backend.expection.AppException;
import com.minhkha.backend.expection.ErrorCode;
import com.minhkha.backend.expection.JwtProvider;
import com.minhkha.backend.mapper.UserMapper;
import com.minhkha.backend.repository.UserRepository;
import com.minhkha.backend.strategy.AuthStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    AuthStrategyFactory authStrategyFactory;
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;

    public AuthenticationResponse login(AuthRequest request, AuthProvider authProvider) {
        return authStrategyFactory.getStrategy(authProvider).login(request);
    }

    public AuthenticationResponse register(UserCreateRequest request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXIST);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        userRepository.save(user);

        return authStrategyFactory.getStrategy(AuthProvider.EMAIL).login(
                AuthRequest.builder()
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .build()
        );
    }


    public IntrospectResponse introspect(IntrospectRequest request) {
        boolean isValid = true;
        try {
            jwtProvider.verifyToken(request.getToken());
        } catch (AppException ex) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .isValid(isValid)
                .build();
    }


}
