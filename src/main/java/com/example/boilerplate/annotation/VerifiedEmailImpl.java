package com.example.boilerplate.annotation;

import com.example.boilerplate.user.auth.AuthService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration
public class VerifiedEmailImpl {
    @Autowired
    private AuthService authService;

    @Before("@annotation(com.example.boilerplate.annotation.VerifiedEmail)")
    public void before() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        authService.checkEmailVerificationStatus(request);
    }
}
