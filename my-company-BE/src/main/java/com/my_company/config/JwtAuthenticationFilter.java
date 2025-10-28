package com.my_company.config;

import com.my_company.constants.TextConstants;
import com.my_company.constants.enums.ErrorCode;
import com.my_company.domain.entity.authentication.User;
import com.my_company.exception.UserAuthenticationException;
import com.my_company.service.authentication.UserService;
import com.my_company.utils.JwtUtils;
import com.my_company.utils.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

        if (JwtUtils.isTokenExpired(token)) {
            throw new UserAuthenticationException(ErrorCode.TOKEN_EXPIRED, TextConstants.TOKEN_EXPIRED_MESSAGE);
        }

        username = JwtUtils.extractUsername(token);

        if (!StringUtils.isNullOrBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            User user = userService.findAuthenticationUserByUsername(username);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    new org.springframework.security.core.userdetails.User(username, user.getPassword(), List.of()),
                    null,
                    List.of()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

}
