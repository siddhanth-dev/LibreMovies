package com.mvlb.libremovie.config;

import com.mvlb.libremovie.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1️⃣ UserDetailsService bean
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities("ROLE_" + user.getRole()) // Adds ROLE_ prefix (e.g., ROLE_ADMIN)
                        .build())
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    // 2️⃣ PasswordEncoder bean
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3️⃣ AuthenticationProvider bean
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // 4️⃣ Security filter chain
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Keep disabled for simple projects
            .authorizeHttpRequests(auth -> auth
                // 1. Static Resources & Public Pages
                .requestMatchers("/", "/login", "/signup", "/css/**", "/js/**").permitAll()

                // 2. ADMIN ONLY: Add, Save, Edit, Update, Delete
                // I added "/movies/edit/**" and "/movies/update" to be safe
                .requestMatchers("/movies/add", "/movies/save", "/movies/edit/**", "/movies/update", "/movies/delete/**").hasRole("ADMIN")

                // 3. EVERYONE (User & Admin): Can view the list
                .requestMatchers("/movies", "/movies/**").hasAnyRole("USER", "ADMIN")

                // 4. Catch-all
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/movies", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}