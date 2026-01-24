package com.ssnhealthcare.drugstore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // PUBLIC
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // ADMIN
                        .requestMatchers("/employee/**", "/report/**", "/category/**")
                        .hasRole("ADMIN")

                        // STOCK MANAGER
                        .requestMatchers("/inventory/**", "/purchase/**", "/alert/**")
                        .hasAnyRole("ADMIN", "STOCK_MANAGER")

                        // PHARMACIST
                        .requestMatchers("/sale/**", "/returns/**")
                        .hasAnyRole("ADMIN", "PHARMACIST")

                        // CUSTOMER
                        .requestMatchers("/customer/**", "/order/**")
                        .hasRole("CUSTOMER")

                        // DASHBOARD
                        .requestMatchers("/dashboard/**")
                        .hasAnyRole("ADMIN", "PHARMACIST", "STOCK_MANAGER")

                        // DRUG READ
                        .requestMatchers(HttpMethod.GET, "/drug/**").authenticated()

                        // DRUG WRITE
                        .requestMatchers(HttpMethod.POST, "/drug/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/drug/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/drug/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
