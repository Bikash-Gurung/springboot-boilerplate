package com.example.boilerplate.redis;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.TemporalType;

import java.util.UUID;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@RedisHash("AuthToken")
public class AuthToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Indexed
    private UUID userId;

    @Indexed
    private String jwtToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt = LocalDateTime.now();
}

