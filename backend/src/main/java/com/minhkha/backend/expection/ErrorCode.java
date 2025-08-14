package com.minhkha.backend.expection;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED(1001, "Bạn không có quyền truy cập", HttpStatus.FORBIDDEN),
    UNAUTHENTICATED(1002, "Bạn chưa đăng nhập", HttpStatus.UNAUTHORIZED),
    INVALID_AUTH_PROVIDER(1003, "Method login không tồn tại", HttpStatus.BAD_REQUEST)
    ,
    PASSWORD_EMAIL_NOT_BLANK(1004, "Email và Password không được để trống", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005, "Tài khoản không tồn tại trong hệ thống", HttpStatus.NOT_FOUND),
    PASSWORD_NOT_MATCH(1006, "Mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),
    USER_ALREADY_EXIST(1007,"Email đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_ARGUMENT(1008, "Lỗi validation", HttpStatus.BAD_REQUEST),
    ;
    int code;
    String message;
    HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
