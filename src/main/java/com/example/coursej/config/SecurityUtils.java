package com.example.coursej.config;

import com.example.coursej.user.User;
import com.example.coursej.user.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
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
