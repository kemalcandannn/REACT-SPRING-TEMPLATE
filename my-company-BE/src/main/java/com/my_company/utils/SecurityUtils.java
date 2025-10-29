package com.my_company.utils;

import com.my_company.constants.ApplicationConstants;
import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.constants.enums.RoleCode;
import com.my_company.exception.UserAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Objects;

public class SecurityUtils {
    private SecurityUtils() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication) ||
                Objects.isNull(authentication.getPrincipal()) ||
                !(authentication.getPrincipal() instanceof User user)) {
            throw new UserAuthenticationException(
                    ErrorCode.TOKEN_VALIDATION_ERROR,
                    TextConstants.TOKEN_VALIDATION_ERROR_MESSAGE);
        }

        return user.getUsername();
    }

    public static List<SimpleGrantedAuthority> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities() == null) {
            return List.of();
        }
        return authentication.getAuthorities().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .toList();
    }

    /**
     * Checks whether the currently authenticated user has a specific role.
     */
    public static boolean hasRole(RoleCode roleCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities() == null) {
            return false;
        }

        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(roleCode.name()::equals);
    }
}
