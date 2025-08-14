package com.minhkha.backend.strategy;

import com.minhkha.backend.dto.request.AuthRequest;
import com.minhkha.backend.dto.response.AuthenticationResponse;
import com.minhkha.backend.eums.AuthProvider;

public interface AuthStrategy {
    AuthenticationResponse login(AuthRequest request);
    AuthProvider getProvider();
}
