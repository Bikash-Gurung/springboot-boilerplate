package com.example.boilerplate.annotation;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Documented
@AuthenticationPrincipal
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE})
public @interface CurrentUser {

}