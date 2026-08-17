package com.ecom.product_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                // REST API
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // =====================================
                        // GET → USER or ADMIN
                        // =====================================

                        .requestMatchers(
                                HttpMethod.GET,
                                "/products/**"
                        ).authenticated()

                        .requestMatchers(
                                HttpMethod.GET,
                                "/categories/**"
                        ).authenticated()


                        // =====================================
                        // POST → ADMIN
                        // =====================================

                        .requestMatchers(
                                HttpMethod.POST,
                                "/products/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.POST,
                                "/categories/**"
                        ).hasRole("ADMIN")


                        // =====================================
                        // PUT → ADMIN
                        // =====================================

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/products/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/categories/**"
                        ).hasRole("ADMIN")


                        // =====================================
                        // DELETE → ADMIN
                        // =====================================

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/products/**"
                        ).hasRole("ADMIN")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/categories/**"
                        ).hasRole("ADMIN")


                        // =====================================
                        // Other endpoints
                        // =====================================

                        .anyRequest().permitAll()
                )

                // =====================================
                // KEYCLOAK JWT
                // =====================================

                .oauth2ResourceServer(oauth2 ->
                        oauth2
                                .jwt(jwt ->
                                        jwt.jwtAuthenticationConverter(
                                                new KeycloakRoleConverter()
                                        )
                                )
                );

        return http.build();
    }
}