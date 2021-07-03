package com.example.boilerplate.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthTokenRepository extends CrudRepository<AuthToken, String> {
    Optional<AuthToken> findByJwtToken(String jwtToken);

    List<AuthToken> findAllByUserId(UUID userId);
}

