package com.example.boilerplate.redis;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface AuthTokenRepository extends CrudRepository<AuthToken, String> {
    Optional<AuthToken> findByJwtToken(String jwtToken);

    List<AuthToken> findAllByUserId(UUID userId);
}

