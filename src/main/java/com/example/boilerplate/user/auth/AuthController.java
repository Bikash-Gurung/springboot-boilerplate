package com.example.boilerplate.user.auth;

import com.example.boilerplate.annotation.CurrentUser;
import com.example.boilerplate.security.UserPrincipal;
import com.example.boilerplate.user.auth.dto.EmailVerificationRequest;
import com.example.boilerplate.user.auth.dto.JWTAuthenticationResponse;
import com.example.boilerplate.user.auth.dto.TokenRequest;
import com.example.boilerplate.user.auth.dto.LoginRequest;
import com.example.boilerplate.user.auth.dto.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public JWTAuthenticationResponse registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.register(signUpRequest);
    }

    @PostMapping("/login")
    public JWTAuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new JWTAuthenticationResponse(authService.logIn(loginRequest));
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('USER')")
    public void logOut(@CurrentUser UserPrincipal userPrincipal) {
        authService.logOut(userPrincipal.getId());
    }

    @PostMapping("/token")
    public JWTAuthenticationResponse refreshToken(@Valid @RequestBody TokenRequest tokenRequest) {
        return authService.refreshToken(tokenRequest);
    }

    @PostMapping("/verify/email")
    @PreAuthorize("hasRole('USER')")
    public String verifyEmail(@CurrentUser UserPrincipal userPrincipal,
                               @Valid @RequestBody EmailVerificationRequest emailVerificationRequest) {
        authService.verifyEmail(userPrincipal.getId(), emailVerificationRequest);

        return "Email verified successfully";
    }
}
