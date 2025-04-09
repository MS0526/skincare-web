// ✅ SecurityConfig.java
package com.example.SkinCare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/", "/login", "/register", "/user/register",
                                                                "/doLogin", "/home", "/mypage")
                                                .permitAll()
                                                .anyRequest().permitAll() // 권한 체크는 컨트롤러에서 함
                                )
                                .formLogin(form -> form.disable())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/home")
                                                .permitAll())
                                .csrf(csrf -> csrf.disable())
                                .build();
        }
}
