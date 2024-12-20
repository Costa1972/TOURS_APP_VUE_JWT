package ru.costa.TOURS_APP_VUE_JWT.security.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class CookieUtil {
    private final String ACCESS_TOKEN_COOKIE_NAME = "access_token";
    private final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    public void setAccessToken(HttpServletResponse response, String accessToken, int age) {
        Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE_NAME, accessToken);
        cookie.setPath("/");
        cookie.setMaxAge(age);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        response.addCookie(cookie);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void setRefreshToken(HttpServletResponse response, String refreshToken, int age) {
        ResponseCookie responseCookie = ResponseCookie
                .from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .path("/")
                .maxAge(age)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

    public void clearAccessToken(HttpServletResponse response) {
        Cookie cookie = new Cookie(ACCESS_TOKEN_COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public String getAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie ->
                    cookie.getName().equals(ACCESS_TOKEN_COOKIE_NAME))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    public void clearRefreshToken(HttpServletResponse response) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
