package com.mvlb.libremovie.config;

import com.mvlb.libremovie.entity.User;
import com.mvlb.libremovie.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner createAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); 
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println(" SUPER ADMIN CREATED: Username: 'admin', Password: 'admin123'");
            }
        };
    }
}