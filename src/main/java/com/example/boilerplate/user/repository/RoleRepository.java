package com.example.boilerplate.user.repository;

import com.example.boilerplate.entity.Role;
import com.example.boilerplate.enums.RoleName;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
