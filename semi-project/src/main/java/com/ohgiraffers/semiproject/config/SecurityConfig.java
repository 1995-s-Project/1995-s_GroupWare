package com.ohgiraffers.semiproject.config;

import com.ohgiraffers.semiproject.common.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler; // CustomAccessDeniedHandler 주입

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 정적 리소스에 대한 요청은 시큐리티 설정이 돌지 못하게 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/home/css/**", "/img/**"); // CSS 파일 및 사진 접근 허용
    }


    /* comment. 여기가 설정의 핵심 */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
                    // 인증되지 않은 사용자에게 허용되는 URL
                    auth.requestMatchers("/home", "/home/no-search", "/home/no-check", "/home/pass-search", "/", "/user/signup").permitAll();

                    // 관리자 권한이 필요한 URL
                    auth.requestMatchers("/manager", "/employeeRegister", "/employeeManagement", "/approvalBox")
                            .hasAuthority(UserRole.ADMIN.getRole());

                    // 일반 사용자 및 관리자 권한이 필요한 URL
                    auth.requestMatchers("/main", "/schedule", "/messenger", "/mail", "/adoption", "/animals",
                                    "/doptionComplete", "/stock", "/facilities", "/board", "/mypage")
                            .hasAnyAuthority(UserRole.USER.getRole(), UserRole.ADMIN.getRole());

                    // 인증된 사용자만 접근 가능한 URL
                    auth.requestMatchers("/user/info", "/schedule/checkin", "/schedule/checkout", "/api/board")
                            .authenticated();

                    // 그 외 모든 요청은 인증된 사용자만 접근 가능
                    auth.anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/home");
                    login.usernameParameter("code");
                    login.passwordParameter("pass");
                    login.defaultSuccessUrl("/main", true);
                    login.failureUrl("/home?error=true");
                })
                .logout(logout -> {
                    logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
                    logout.deleteCookies("JSESSIONID");
                    logout.invalidateHttpSession(true);
                    logout.logoutSuccessUrl("/home");
                })
                .sessionManagement(session -> {
                    session.maximumSessions(1);
                    session.invalidSessionUrl("/home");
                })
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler)); // 접근 거부 핸들러 설정

        return http.build();
    }

}