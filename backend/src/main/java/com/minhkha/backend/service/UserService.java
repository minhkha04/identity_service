package com.minhkha.backend.service;

import com.minhkha.backend.dto.response.UserResponse;
import com.minhkha.backend.entity.User;
import com.minhkha.backend.expection.AppException;
import com.minhkha.backend.expection.ErrorCode;
import com.minhkha.backend.mapper.UserMapper;
import com.minhkha.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {

     UserRepository userRepository;
     UserMapper userMapper;

     public UserResponse getMyInfo() {
         String userId = SecurityContextHolder.getContext().getAuthentication().getName();
         User user = userRepository.findById(userId)
                 .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

         return userMapper.toUserResponse(user);

     }
}
