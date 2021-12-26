package com.example.boilerplate.user.repository;

import com.example.boilerplate.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID userId);

    boolean existsByEmail(String email);
}
