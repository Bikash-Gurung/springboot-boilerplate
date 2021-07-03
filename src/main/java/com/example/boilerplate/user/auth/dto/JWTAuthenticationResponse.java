package com.example.boilerplate.user.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JWTAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
