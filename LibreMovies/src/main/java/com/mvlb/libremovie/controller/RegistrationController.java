package com.mvlb.libremovie.controller;

import com.mvlb.libremovie.entity.User;
import com.mvlb.libremovie.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute User user) {

        // 🔹 If username already exists → redirect to login
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "redirect:/login?error=already_registered";
        }

        // 🔹 Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 🔹 Assign default role
        user.setRole("USER");

        // 🔹 Save new user
        userRepository.save(user);

        return "redirect:/login?registered=true";
    }
}
