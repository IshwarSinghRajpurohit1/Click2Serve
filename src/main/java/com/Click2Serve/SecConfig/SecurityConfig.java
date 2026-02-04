package com.Click2Serve.SecConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/hotels/**",
                                "/rooms/**",
                                "/categories/**",
                                "/menu/**",
                                "/orders/**",
                                "/users/**"  // <-- CSRF ignore for these
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/hotels/**",
                                "/rooms/**",
                                "/categories/**",
                                "/menu/**",
                                "/orders/**",
                                "/users/**"  // <-- permitAll for testing
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
