package com.kwetter.frits.userservice.repository;

import com.kwetter.frits.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(UUID userId);
    Boolean existsUserByUsername(String username);
}
