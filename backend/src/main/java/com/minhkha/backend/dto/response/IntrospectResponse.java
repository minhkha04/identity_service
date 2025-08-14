package com.minhkha.backend.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntrospectResponse {
    boolean isValid;
}
