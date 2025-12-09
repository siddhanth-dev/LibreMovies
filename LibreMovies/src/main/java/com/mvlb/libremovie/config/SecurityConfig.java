package com.mvlb.libremovie.config;

import com.mvlb.libremovie.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1️⃣ UserDetailsService bean
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())  // Must be BCrypt hashed
                        .authorities("ROLE_" + user.getRole())  // Add ROLE_ prefix
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    // 2️⃣ PasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3️⃣ AuthenticationProvider bean
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // 4️⃣ Security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity; optional
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers("/", "/login", "/signup", "/css/**", "/js/**").permitAll()

                // Admin-only endpoints
                .requestMatchers("/movies/add", "/movies/save", "/movies/delete/**").hasRole("ADMIN")

                // User & Admin endpoints
                .requestMatchers("/movies/**").hasAnyRole("USER", "ADMIN")

                // Any other request requires authentication
                .anyRequest().authenticated()
            )
            // Login configuration
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/movies", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            // Logout configuration
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}
