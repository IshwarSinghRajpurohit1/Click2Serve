package com.Click2Serve.SecConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

   // @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/hotels/**", "/menu/**")) // ignore CSRF for /hotels
//                .authorizeHttpRequests(auth -> auth .requestMatchers("/hotels/**", "/menu/**").permitAll().anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
//}


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())          // CSRF poora disable
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()      // sab allow, koi auth nahi
                );

        return http.build();
    }
}


