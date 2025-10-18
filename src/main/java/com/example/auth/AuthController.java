package com.example.auth;

import com.google.auth.oauth2.TokenVerifier;
import com.google.auth.oauth2.TokenVerifier.VerificationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
// import spice.http.server.rest.ExchangeRequest; // Removed to avoid type conflict

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${google.oauth.clientId}") private String clientId;
    @Value("${app.jwt.secret}") private String appSecret;

    // This record is used as a DTO for the Google ID token exchange request.
    public static record ExchangeRequest(String idToken) {}
    @PostMapping("/google")
    public ResponseEntity<Map<String, Object>> exchange(@RequestBody ExchangeRequest body) {
        try {
            var payload = TokenVerifier.newBuilder()
                .setAudience(clientId)
                .build().verify(body.idToken);

            String sub = payload.getPayload().getSubject();
            String email = (String) payload.getPayload().get("email");
            Instant now = Instant.now();
            var key = Keys.hmacShaKeyFor(appSecret.getBytes(StandardCharsets.UTF_8));

            String access = Jwts.builder()
                    .subject(sub)
                    .claim("email", email)
                    .issuedAt(java.util.Date.from(now))
                    .expiration(java.util.Date.from(now.plusSeconds(3600)))
                    .signWith(key, Jwts.SIG.HS256)
                    .compact();

            String refresh = Jwts.builder()
                    .subject(sub)
                    .issuedAt(java.util.Date.from(now))
                    .expiration(java.util.Date.from(now.plusSeconds(60L*60*24*30)))
                    .signWith(key, Jwts.SIG.HS256)
                    .compact();

            Map<String, Object> resp = new HashMap<>();
            resp.put("accessToken", access);
            resp.put("refreshToken", refresh);
            resp.put("expiresIn", 3600);
            return ResponseEntity.ok(resp);
        } catch (VerificationException e) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "Invalid Google ID Token");
            return ResponseEntity.status(401).body(err);
        }
    }
}