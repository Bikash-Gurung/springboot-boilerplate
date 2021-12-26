package com.example.boilerplate.redis;

import com.example.boilerplate.exception.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class AuthTokenService {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    private Logger logger = LoggerFactory.getLogger(AuthTokenService.class);

    public String create(UUID userId, String jwtToken) {
        AuthToken authToken = new AuthToken();
        authToken.setUserId(userId);
        authToken.setJwtToken(jwtToken);
        logger.info("Creating auth token for userId: {}", userId);

        return authTokenRepository.save(authToken).getJwtToken();
    }

    public List<AuthToken> getAllByUserId(UUID userId){
        return authTokenRepository.findAllByUserId(userId);
    }

    public Optional<AuthToken> getAuthToken(String jwtToken) {
        return authTokenRepository.findByJwtToken(jwtToken);
    }

    @Transactional
    public void deleteAuthTokenByUserId(UUID userId) {
        List<AuthToken> authToken = authTokenRepository.findAllByUserId(userId);
        authTokenRepository.deleteAll(authToken);
    }

    public void deleteAuthTokenByJWTToken(String jwtToken) {
        Optional<AuthToken> authToken = authTokenRepository.findByJwtToken(jwtToken);

        if (authToken.isPresent()) {
            logger.info("Deleting token[{}] of sender[{}]", jwtToken, authToken.get().getUserId());
            authTokenRepository.delete(authToken.get());
        } else {
            logger.error("Auth token not found with JWT token: {}", jwtToken);
            throw new ResourceNotFoundException("AuthToken", "JWT token", jwtToken);
        }
    }

    public void removeInactiveTokens() {
        List<AuthToken> authTokens = (List<AuthToken>) authTokenRepository.findAll();

        if (authTokens.size() < 1) {
            authTokens.forEach(this::deleteInactiveToken);
        }
    }

    private void deleteInactiveToken(AuthToken authToken) {
        LocalDateTime timeToDeleteToken = LocalDateTime.now().minusMinutes(10);

        if (authToken.getCreatedAt().isBefore(timeToDeleteToken)) {
            deleteAuthTokenByJWTToken(authToken.getJwtToken());
        }
    }
}
