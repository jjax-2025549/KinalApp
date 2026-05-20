package com.julianjax.kinalapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. Permitir registro público (POST a /usuarios)
                        .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        // 2. Permitir vistas y assets
                        .requestMatchers("/login.html", "/registro.html", "/css/**", "/js/**").permitAll()
                        // 3. Restricciones ADMIN
                        .requestMatchers(HttpMethod.DELETE, "/productos/**", "/clientes/**", "/ventas/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/productos/**", "/clientes/**", "/ventas/**").hasRole("ADMIN")
                        // 4. Acceso al resto
                        .requestMatchers("/dashboard.html", "/productos.html", "/clientes.html", "/ventas.html").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/api/auth/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((request, response, auth) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("{\"status\": \"success\"}");
                        })
                        .failureHandler((request, response, ex) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"status\": \"error\"}");
                        })
                        .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/api/auth/logout").permitAll());

        return http.build();
    }
}