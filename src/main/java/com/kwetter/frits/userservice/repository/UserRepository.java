package com.kwetter.frits.userservice.repository;

import com.kwetter.frits.userservice.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserId(UUID userId);
    Boolean existsUserByUsername(String username);
    User findUserByUsername(String username);
    void deleteUserByUsernameAndUserId(String username, UUID userId);
    Boolean existsUserByUserId(UUID userId);
}
