package com.example.boilerplate.user;

import com.example.boilerplate.entity.User;
import com.example.boilerplate.user.dto.UserInfoResponse;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    UserInfoResponse toUserInfoResponse(User user);
}
