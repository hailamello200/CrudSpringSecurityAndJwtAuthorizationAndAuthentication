package com.spring.demo.infrastructure.security;

import java.io.IOException;
import com.spring.demo.repository.UserRepository;
import com.spring.demo.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService service;

    @Autowired
    UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        var token = this.recoverToken(request);

        if (token != null) {

            var login = service.validateToken(token);
            UserDetails details = repository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null;

        return authHeader.replace("Bearer", "");
    }
}
