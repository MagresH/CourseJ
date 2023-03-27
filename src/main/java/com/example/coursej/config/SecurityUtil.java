package com.example.coursej.config;

import com.example.coursej.model.User;
import com.example.coursej.model.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

public class SecurityUtil {
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
}
