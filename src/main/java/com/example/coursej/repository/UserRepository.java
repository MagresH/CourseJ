package com.example.coursej.repository;

import com.example.coursej.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    Optional<Object> findByUsername(String username);
    // Optional<T> findByUsername(String username);
}
