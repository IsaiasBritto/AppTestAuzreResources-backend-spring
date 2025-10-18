package com.example.auth.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/health", "/ai/**").permitAll() // libere o que quiser
        .anyRequest().authenticated()
      )
      .httpBasic(basic -> basic.disable())
      .formLogin(form -> form.disable());

    return http.build();
  }
}
