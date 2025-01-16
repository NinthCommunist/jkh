package ru.fast.bills.security.web;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.fast.bills.security.data.repository.JwtRepository;
import ru.fast.bills.security.service.JwtProvider;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationJWTFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && SecurityContextHolder.getContext().getAuthentication() == null) {
                String jwt = StringUtils.substringAfter(authorizationHeader, "Bearer ");
                Authentication authentication = this.jwtProvider.parseAuthentication(jwt);

                if (this.tokenRepository.tokenExist(authentication.getName(), jwt)) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    CsrfFilter.skipRequest(request);
                }
            }
        } catch (JwtException jwtException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(jwtException.getLocalizedMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
