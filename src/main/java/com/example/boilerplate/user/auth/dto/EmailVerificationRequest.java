package com.example.boilerplate.user.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class EmailVerificationRequest {

    @Size(max = 6)
    private String verificationCode;
}
