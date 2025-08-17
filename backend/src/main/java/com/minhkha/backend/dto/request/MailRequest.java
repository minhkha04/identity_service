package com.minhkha.backend.dto.request;

import com.minhkha.backend.eums.MailType;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class MailRequest {
    String toEmail;
    String subject;
    MailType mailType;
    Map<String, String> params;
}
