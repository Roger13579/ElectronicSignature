package com.myl.electronicsignatureservice.auth.utils;

import com.myl.electronicsignatureservice.auth.dto.UserLoginRequest;
import com.myl.electronicsignatureservice.auth.properties.AuthProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthProperties authProperties;

    private final String KEY = authProperties.getJwtKey();

    public String generateToken(UserLoginRequest request) throws Exception {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authentication = authenticationConfiguration.getAuthenticationManager().authenticate(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 2);
        Claims claims = Jwts.claims()
                .expiration(calendar.getTime())
                .issuer("JWT Test")
                .build();
        claims.put("username", userDetails.getUsername());
        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    public Map<String, Object> parseToken(String token){
        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());
        JwtParser parser = Jwts.parser()
                .setSigningKey(secretKey)
                .build();
        Claims claims = parser
                .parseClaimsJws(token)
                .getBody();
        return claims.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
