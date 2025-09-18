// ✅ SecurityConfig.java
package com.example.SkinCare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// 이 클래스가 스프링의 설정(Configuration) 클래스임을 나타냅니다.
@Configuration
// 스프링 시큐리티의 웹 보안 기능을 활성화합니다. 이 어노테이션이 있어야 시큐리티 설정이 적용됩니다.
@EnableWebSecurity
public class SecurityConfig {

        // @Bean 어노테이션을 통해 이 메소드가 반환하는 객체(PasswordEncoder)를 스프링 컨테이너에 등록합니다.
        // 이렇게 등록된 Bean은 다른 곳에서 의존성 주입(DI)을 통해 사용할 수 있습니다.
        @Bean
        public PasswordEncoder passwordEncoder() {
                // BCryptPasswordEncoder는 스프링 시큐리티에서 제공하는 비밀번호 암호화 구현체입니다.
                // 비밀번호를 해시 함수를 통해 안전하게 저장할 수 있게 해줍니다.
                return new BCryptPasswordEncoder();
        }

        // @Bean 어노테이션을 통해 이 메소드가 반환하는 SecurityFilterChain 객체를 스프링 컨테이너에 등록합니다.
        // 이 Bean이 실제 스프링 시큐리티의 핵심 설정을 담당합니다.
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                // HttpSecurity 객체를 사용하여 웹 기반의 보안 설정을 구성합니다.
                // 'return http.build()'를 통해 최종적으로 SecurityFilterChain 객체를 생성하여 반환합니다.
                return http
                                // HTTP 요청에 대한 접근 권한을 설정합니다.
                                .authorizeHttpRequests(auth -> auth
                                                // .requestMatchers()를 사용하여 특정 URL 패턴에 대한 규칙을 지정합니다.
                                                // 여기에 명시된 URL들은
                                                .requestMatchers("/", "/login", "/register", "/user/register",
                                                                "/doLogin", "/home", "/mypage")
                                                // .permitAll()을 통해 인증(로그인) 여부와 관계없이 누구나 접근할 수 있도록 허용합니다.
                                                .permitAll()
                                                // .anyRequest()는 위에서 지정한 URL 외의 모든 나머지 요청을 의미합니다.
                                                // .permitAll()로 설정했으므로, 일단 모든 요청을 허용한 뒤 각 컨트롤러에서 세부적인 권한 체크를 하겠다는
                                                // 의도입니다.
                                                .anyRequest().permitAll())
                                // 폼 기반 로그인을 설정하는 부분입니다.
                                // .disable()로 설정했기 때문에, 스프링 시큐리티가 제공하는 기본 로그인 폼과 로그인 처리 기능을 사용하지 않겠다는 의미입니다.
                                // 이 경우, '/doLogin'과 같은 URL을 컨트롤러에서 직접 구현해야 합니다.
                                .formLogin(form -> form.disable())
                                // 로그아웃 관련 설정을 하는 부분입니다.
                                .logout(logout -> logout
                                                // 로그아웃을 처리할 URL을 '/logout'으로 지정합니다.
                                                .logoutUrl("/logout")
                                                // 로그아웃에 성공했을 때 리다이렉트될 URL을 '/home'으로 지정합니다.
                                                .logoutSuccessUrl("/home")
                                                // 로그아웃 URL에 대해서도 모든 사용자가 접근할 수 있도록 허용합니다.
                                                .permitAll())
                                // CSRF(Cross-Site Request Forgery) 공격에 대한 보호 설정을 하는 부분입니다.
                                // .disable()로 설정하여 CSRF 보호 기능을 비활성화했습니다.
                                // 개발 편의를 위해 비활성화하는 경우가 많지만, 실제 서비스 배포 시에는 활성화하는 것이 보안상 안전합니다.
                                .csrf(csrf -> csrf.disable())
                                // 위에서 설정한 모든 HttpSecurity 구성을 바탕으로 SecurityFilterChain 객체를 생성합니다.
                                .build();
        }
}