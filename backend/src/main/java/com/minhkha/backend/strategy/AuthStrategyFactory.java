package com.minhkha.backend.strategy;

import com.minhkha.backend.eums.AuthProvider;
import com.minhkha.backend.expection.AppException;
import com.minhkha.backend.expection.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthStrategyFactory {

    List<AuthStrategy> strategies;

    public AuthStrategy getStrategy(AuthProvider authProvider) {

        return  strategies.stream()
                .filter(strategy -> strategy.getProvider().equals(authProvider))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_AUTH_PROVIDER));
    }

}
