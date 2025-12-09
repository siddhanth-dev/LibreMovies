package com.mvlb.libremovie.controller;

import com.mvlb.libremovie.entity.Movie;
import com.mvlb.libremovie.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal; // 👈 IMPORTANT IMPORT
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    // List movies with pagination (USER SPECIFIC)
    @GetMapping("")
    public String listMovies(Model model, 
                             @RequestParam(defaultValue = "1") int page, 
                             @RequestParam(required = false) String keyword,
                             Principal principal) { // 1. Inject Principal to get current user

        String username = principal.getName(); // Get "admin" or "siddhanth"
        int pageSize = 5;
        
        // 2. CHECK: Is the user searching?
        if (keyword != null && !keyword.isEmpty()) {
            // --- SEARCH MODE (User Specific) ---
            List<Movie> searchResults = service.searchMovies(keyword, username);
            
            model.addAttribute("movies", searchResults);
            model.addAttribute("keyword", keyword);
            
            // Fake pagination for search results
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", 1);
            model.addAttribute("totalItems", searchResults.size());
            model.addAttribute("pageSize", searchResults.size()); 
        } 
        else {
            // --- NORMAL PAGINATION MODE (User Specific) ---
            Page<Movie> moviePage = service.getPaginatedMovies(page, pageSize, username);
            
            model.addAttribute("movies", moviePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", moviePage.getTotalPages());
            model.addAttribute("totalItems", moviePage.getTotalElements());
            model.addAttribute("pageSize", pageSize);
        }

        return "movies";
    }

    @GetMapping("/add")
    public String addMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "addmovie";
    }

    // Save Movie (Now attaches the user!)
    @PostMapping("/save")
    public String saveMovie(@ModelAttribute Movie movie, Principal principal) {
        service.save(movie, principal.getName()); // Pass username to Service
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String editMovieForm(@PathVariable Long id, Model model) {
        Movie movie = service.getMovieById(id);
        model.addAttribute("movie", movie);
        return "editmovie";
    }

    // Update Movie (Now attaches the user!)
    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable Long id, @ModelAttribute Movie movie, Principal principal) {
        movie.setId(id);
        service.save(movie, principal.getName()); // Pass username to Service
        return "redirect:/movies";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/movies";
    }
}