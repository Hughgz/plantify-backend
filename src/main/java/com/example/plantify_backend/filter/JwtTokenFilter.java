package com.example.plantify_backend.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.plantify_backend.models.Users;
import com.example.plantify_backend.utils.JWTTokenUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTTokenUtils jwtTokenUtils;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {

        try {
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
        final String token = authHeader.substring(7);

        final String phoneNumber = jwtTokenUtils.extractPhone(token);

        if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Users existingUser = (Users) userDetailsService.loadUserByUsername(phoneNumber);
            if (jwtTokenUtils.validateToken(token, existingUser)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(existingUser, null, existingUser.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
    private boolean isBypassToken(@NotNull HttpServletRequest request) {
        final List<String> bypassUrls = Arrays.asList(
                "/api/user/login",
                "/api/user/register"
        );
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();

        // ✅ Kiểm tra nếu URL thuộc danh sách bỏ qua
        return (bypassUrls.contains(requestURI) && "POST".equalsIgnoreCase(requestMethod));
    }
}
