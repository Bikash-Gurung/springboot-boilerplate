package com.example.boilerplate.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {
    private String name;
    private String email;
    private boolean isEmailVerified;
}
