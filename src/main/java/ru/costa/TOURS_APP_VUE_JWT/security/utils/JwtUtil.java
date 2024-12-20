package ru.costa.TOURS_APP_VUE_JWT.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.costa.TOURS_APP_VUE_JWT.models.Role;
import ru.costa.TOURS_APP_VUE_JWT.models.User;
import ru.costa.TOURS_APP_VUE_JWT.repository.RoleRepository;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    private final RoleRepository roleRepository;

    @Value("${jwt.secretPhrase}")
    private String secretPhrase;
    @Value("${jwt.expirationToken}")
    private Long expirationToken;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretPhrase));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("username", customUserDetails.getUsername());
            claims.put("role", customUserDetails.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        }
        return getAuthenticationToken(claims, userDetails);
    }
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public String getAuthenticationToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        Date now = new Date();
        Date expiration = Date.from(LocalDateTime.now().plusMinutes(expirationToken)
                .atZone(ZoneId.systemDefault()).toInstant());
        try {
            return Jwts.builder()
                    .claims(extraClaims)
                    .id(id)
                    .subject(userDetails.getUsername())
                    .issuedAt(now)
                    .expiration(expiration)
//                    .claim("authorities", userDetails.getAuthorities())
                    .signWith(getSecretKey())
                    .compact();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return null;
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

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }
        return  false;
    }

    public void clearToken(HttpServletResponse servletResponse) {
        if (servletResponse.getHeaderNames().contains("Set-Cookie")) {
            servletResponse.setHeader("Set-Cookie", "");
        }
    }
}
