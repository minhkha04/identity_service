package com.minhkha.backend.service;

import com.minhkha.backend.dto.request.*;
import com.minhkha.backend.dto.response.AuthenticationResponse;
import com.minhkha.backend.dto.response.IntrospectResponse;
import com.minhkha.backend.entity.User;
import com.minhkha.backend.eums.*;
import com.minhkha.backend.expection.AppException;
import com.minhkha.backend.expection.ErrorCode;
import com.minhkha.backend.expection.JwtProvider;
import com.minhkha.backend.mapper.UserMapper;
import com.minhkha.backend.repository.UserRepository;
import com.minhkha.backend.strategy.AuthStrategyFactory;
import com.minhkha.backend.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    AuthStrategyFactory authStrategyFactory;
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;
    MailService mailService;
    MailOtpService mailOtpService;

    public AuthenticationResponse login(AuthRequest request, AuthProvider authProvider) {
        return authStrategyFactory.getStrategy(authProvider).login(request);
    }

    public void register(UserCreateRequest request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXIST);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        userRepository.save(user);
        String otp = OtpUtil.generateOtp();
        mailOtpService.create(request.getEmail(), otp);
        MailRequest mailRequest = MailRequest.builder()
                .mailType(MailType.VERIFY_EMAIL)
                .toEmail(request.getEmail())
                .subject("Verify your email")
                .params(Map.of("otp", otp))
                .build();
        mailService.sendMail(mailRequest);
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

    public void resendOtpRequest(ResendOtpRequest request, ResendOtpType type) {
        switch (type) {
            case REGISTER -> {
                User user = userRepository.findUserByEmail(request.getEmail())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                String otp = OtpUtil.generateOtp();
                mailOtpService.create(user.getEmail(), otp);
                MailRequest mailRequest = MailRequest.builder()
                        .mailType(MailType.VERIFY_EMAIL)
                        .toEmail(user.getEmail())
                        .subject("Verify your email")
                        .params(Map.of("otp", otp))
                        .build();
                mailService.sendMail(mailRequest);
            }
            case RESET_PASSWORD -> {
                User user = userRepository.findUserByEmail(request.getEmail())
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
                String otp = OtpUtil.generateOtp();
                mailOtpService.create(user.getEmail(), otp);
                MailRequest mailRequest = MailRequest.builder()
                        .mailType(MailType.RESET_PASSWORD)
                        .toEmail(user.getEmail())
                        .subject("Change your password")
                        .params(Map.of("otp", otp))
                        .build();
                mailService.sendMail(mailRequest);
            }
            default -> throw new AppException(ErrorCode.RESEND_OTP_TYPE_INVALID);
        }
    }

    public AuthenticationResponse verifyOtp(VerifyOtpRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        mailOtpService.verify(request.getEmail(), request.getOtp());
        return AuthenticationResponse.builder()
                .token(jwtProvider.generateToken(user))
                .build();
    }

    public AuthenticationResponse resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        mailOtpService.verify(request.getEmail(), request.getOtp());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(jwtProvider.generateToken(user))
                .build();
    }
}
