package com.example.boilerplate.user.auth;

import com.example.boilerplate.entity.Role;
import com.example.boilerplate.entity.User;
import com.example.boilerplate.enums.RoleName;
import com.example.boilerplate.redis.AuthToken;
import com.example.boilerplate.user.UserService;
import com.example.boilerplate.mail.MailService;
import com.example.boilerplate.enums.EmailTemplate;
import com.example.boilerplate.exception.AppException;
import com.example.boilerplate.redis.AuthTokenService;
import com.example.boilerplate.security.JWTTokenProvider;
import com.example.boilerplate.user.auth.dto.TokenRequest;
import com.example.boilerplate.user.auth.dto.LoginRequest;
import com.example.boilerplate.user.auth.dto.SignUpRequest;
import com.example.boilerplate.util.HttpServletRequestUtils;
import com.example.boilerplate.exception.BadRequestException;
import com.example.boilerplate.user.repository.RoleRepository;
import com.example.boilerplate.user.repository.UserRepository;
import com.example.boilerplate.common.VerificationCodeGenerator;
import com.example.boilerplate.exception.UnauthorizedAccessException;
import com.example.boilerplate.user.auth.dto.EmailVerificationRequest;
import com.example.boilerplate.user.auth.dto.JWTAuthenticationResponse;

import freemarker.template.TemplateException;

import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;

import java.util.*;
import java.io.IOException;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private MessageSource messageSource;

    @Transactional
    public JWTAuthenticationResponse register(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already exist");
        }

        User user = userRepository.save(buildUser(signUpRequest));
        String jwtToken = jwtTokenProvider.generateToken();
        authTokenService.create(user.getId(), jwtToken);

        Locale nepali = new Locale("ne", "NP");

        try {
//            mailService.sendEmail(user, EmailTemplate.EMAIL_VERIFICATION);
            mailService.sendEmailWithLocale(user, EmailTemplate.GREETING, nepali);
        } catch (MessagingException | IOException | TemplateException e) {
            logger.error(messageSource.getMessage("unableToSendEmail", new Object[] {user.getEmail()}, nepali));
        }

        return new JWTAuthenticationResponse(jwtToken);
    }

    private User buildUser(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("Unable to set User Role"));
        user.setRoles(Collections.singleton(userRole));
        user.setVerificationCode(VerificationCodeGenerator.generate());

        return user;
    }

    public String logIn(LoginRequest loginRequest) {
        String jwtToken = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        User user = userRepository.findByEmail(loginRequest.getEmail()).get();
        List<AuthToken> authTokens = authTokenService.getAllByUserId(user.getId());

        if (!authTokens.isEmpty()) {
            authTokenService.deleteAuthTokenByUserId(user.getId());
        }

        authTokenService.create(user.getId(), jwtToken);

        return jwtToken;
    }

    private String authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken();
    }

    public void logOut(UUID userId) {
        authTokenService.deleteAuthTokenByUserId(userId);
    }

    public JWTAuthenticationResponse refreshToken(TokenRequest tokenRequest) {
        Optional<AuthToken> authToken = authTokenService.getAuthToken(tokenRequest.getToken());

        if (authToken.isPresent()) {
            authTokenService.deleteAuthTokenByJWTToken(tokenRequest.getToken());

            return new JWTAuthenticationResponse(jwtTokenProvider.generateToken());
        } else {
            throw new BadRequestException("Invalid token");
        }
    }

    public void checkEmailVerificationStatus(HttpServletRequest request) {
        String referenceToken = HttpServletRequestUtils.getReferenceToken(request);
        Optional<AuthToken> authToken = authTokenService.getAuthToken(referenceToken);

        if (!authToken.isPresent()) {
            throw new BadRequestException("Invalid access token");
        }

        User user = userRepository.findById(authToken.get().getUserId()).get();

        if (!user.isEmailVerified()) {
            throw new UnauthorizedAccessException("Looks like your email verification is pending. Please verify your " +
                    "email first");
        }
    }

    public void verifyEmail(UUID userId, EmailVerificationRequest emailVerificationRequest) {
        User user = userService.getUserById(userId);

        if (!user.getVerificationCode().equals(emailVerificationRequest.getVerificationCode())) {
            throw new BadRequestException("Invalid verification code");
        }

        user.setEmailVerified(true);
        userRepository.save(user);
    }
}
