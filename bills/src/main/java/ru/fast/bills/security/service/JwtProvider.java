package ru.fast.bills.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.fast.bills.security.web.dto.TokenResponse;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtProvider {

    @Value("${app.security.jwt.secret}")
    private String secret;

    @Value("${app.security.jwt.access.expiration}")
    private Duration accessExpiration;

    @Value("${app.security.jwt.refresh.expiration}")
    private Duration refreshExpiration;

    public TokenResponse tokensFor(Authentication authentication) {
        TokenWithExpiration access = this.accessTokenFor(authentication);
        TokenWithExpiration refresh = this.refreshTokenFor(authentication);

        return new TokenResponse(access.token, refresh.token, access.expiration, refresh.expiration);
    }

    public TokenWithExpiration accessTokenFor(final Authentication authentication) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + this.accessExpiration.toMillis());
        String token = this.generateToken(authentication, issuedAt, expiration);
        return new TokenWithExpiration(token, expiration);
    }

    private TokenWithExpiration refreshTokenFor(final Authentication authentication) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + this.refreshExpiration.toMillis());
        String token = this.generateToken(authentication, issuedAt, expiration);
        return new TokenWithExpiration(token, expiration);
    }

    private String generateToken(Authentication authentication, Date issuedAt, Date expiration) {
        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .subject(authentication.getName())
                .claims(
                        Map.of("roles", authentication.getAuthorities()
                                .stream().map(GrantedAuthority::getAuthority).toList()))
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(this.computeSignKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey computeSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret));
    }


    public Authentication parseAuthentication(String jwt) {
        Claims claims = Jwts.parser()
                .verifyWith(this.computeSignKey()).build()
                .parseSignedClaims(jwt)
                .getPayload();
        List<String> roles = claims.get("roles", List.class);
        return new UsernamePasswordAuthenticationToken(claims.getSubject(),
                null,
                roles.stream().map(SimpleGrantedAuthority::new).toList());
    }

    public record TokenWithExpiration(String token, Date expiration) {
    }
}
