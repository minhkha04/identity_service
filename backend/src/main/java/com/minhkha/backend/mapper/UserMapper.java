package com.minhkha.backend.mapper;

import com.minhkha.backend.dto.request.UserCreateRequest;
import com.minhkha.backend.dto.response.UserResponse;
import com.minhkha.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User toUser(UserCreateRequest request);

    UserResponse toUserResponse(User user);
}
