package com.mvlb.libremovie.controller;


import com.mvlb.libremovie.entity.Movie;
import com.mvlb.libremovie.service.MovieService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    // List movies with pagination
    @GetMapping("")
    public String listMovies(Model model, 
                             @RequestParam(defaultValue = "1") int page, 
                             @RequestParam(required = false) String keyword) { // 1. Add keyword param

        int pageSize = 5;
        
        // 2. CHECK: Is the user searching?
        if (keyword != null && !keyword.isEmpty()) {
            // --- SEARCH MODE ---
            List<Movie> searchResults = service.searchMovies(keyword);
            
            model.addAttribute("movies", searchResults);
            model.addAttribute("keyword", keyword); // Send back to keep input filled
            
            // Fake the pagination data so the HTML doesn't crash
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", 1);
            model.addAttribute("totalItems", searchResults.size());
            model.addAttribute("pageSize", searchResults.size()); 
        } 
        else {
            // --- NORMAL PAGINATION MODE (Your original code) ---
            Page<Movie> moviePage = service.getPaginatedMovies(page, pageSize);
            
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

    @PostMapping("/save")
    public String saveMovie(@ModelAttribute Movie movie) {
        service.saveMovie(movie);
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String editMovieForm(@PathVariable Integer id, Model model) {
        Movie movie = service.getMovieById(id);
        model.addAttribute("movie", movie);
        return "editmovie";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable Integer id, @ModelAttribute Movie movie) {
        movie.setId(id);
        service.saveMovie(movie);
        return "redirect:/movies";
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        service.deleteMovie(id);
        return "redirect:/movies";
    }
}
