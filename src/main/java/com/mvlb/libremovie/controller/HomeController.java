package com.mvlb.libremovie.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Authentication authentication) {

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/signup";
        }

        return "redirect:/movies";
    }
}
