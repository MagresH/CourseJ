package com.example.coursej.config;

import com.example.coursej.user.User;
import com.example.coursej.user.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public static User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found");
        }
        return (User)authentication.getPrincipal();
    }

    public static boolean isCurrentUser(Long userId) {
        return getPrincipal().getId().equals(userId);
    }

    public static boolean isAdmin(Long userId) {
        return getPrincipal().getRole().equals(UserRole.ADMIN);
    }

    public static boolean isCurrentUserOrAdmin(Long userId) {
        return isCurrentUser(userId) || isAdmin(userId);
    }
}
