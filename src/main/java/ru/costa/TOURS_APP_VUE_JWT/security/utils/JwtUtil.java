package ru.costa.TOURS_APP_VUE_JWT.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.costa.TOURS_APP_VUE_JWT.models.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secretPhrase}")
    private String secretPhrase;
    @Value("${jwt.expirationToken}")
    private String expirationToken;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretPhrase));
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .signWith(getSecretKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(expirationToken)))
                .compact();
    }

    public String getAuthenticationToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("username", customUserDetails.getUsername());
            claims.put("lastName", customUserDetails.getLastName());
            claims.put("firstName", customUserDetails.getFirstName());
            claims.put("patronymic", customUserDetails.getPatronymic());
            claims.put("phone", customUserDetails.getPhone());
            claims.put("roles", customUserDetails.getRoles());
        }
        return generateToken(claims, userDetails);
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token %s ".formatted(token), e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token %s is expired".formatted(token), e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token %s is unsupported".formatted(token), e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT token %s is invalid".formatted(token), e.getMessage());
        }
        return false;
    }
}
