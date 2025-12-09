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
            // Check if admin exists. If not, create one.
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // Set your master password
                admin.setRole("ADMIN");
                userRepository.save(admin);
                System.out.println("✅ SUPER ADMIN CREATED: Username: 'admin', Password: 'admin123'");
            }
        };
    }
}