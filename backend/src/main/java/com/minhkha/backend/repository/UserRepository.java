package com.minhkha.backend.repository;

import com.minhkha.backend.entity.User;
import com.minhkha.backend.eums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsUserByRole(Role role);

    boolean existsUserByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
