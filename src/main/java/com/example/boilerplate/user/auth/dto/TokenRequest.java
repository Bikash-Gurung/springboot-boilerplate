package com.example.boilerplate.user.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenRequest {
    @NotBlank
    private String token;
}
