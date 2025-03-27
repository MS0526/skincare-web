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
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/login", "/register").permitAll() // 로그인, 회원가입 페이지는 누구나 접근 가능
                        .anyRequest().authenticated() // 그 외 페이지는 로그인한 사용자만 접근 가능
                )
                .formLogin(form -> form
                        .loginPage("/login") // 커스터마이징된 로그인 페이지로 이동
                        .defaultSuccessUrl("/home", true) // 로그인 성공 후 홈 페이지로 이동
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/index")
                        .permitAll());

        return http.build();
    }
}
