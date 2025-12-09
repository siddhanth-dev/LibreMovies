package com.mvlb.libremovie.controller;

import com.mvlb.libremovie.repository.MovieRepository;
import com.mvlb.libremovie.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mvlb.libremovie.entity.Movie;
import com.mvlb.libremovie.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    public AdminController(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin_users"; // We need to create this HTML file
    }
 // 2. Delete a User (And all their movies automatically)
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }

    // 3. View a specific User's library
    @GetMapping("/users/{id}/movies")
    public String viewUserMovies(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        // Just show all their movies (no pagination for simplicity in admin view)
        // Note: Make sure MovieRepository has findByUser method returning List or Page
        model.addAttribute("movies", movieRepository.findByUser(user, PageRequest.of(0, 1000)).getContent());
        model.addAttribute("owner", user.getUsername());
        return "admin_movie_view"; // We will make this new file
    }
    
    // 4. Admin deletes a specific movie from a user's list
    @GetMapping("/movies/delete/{id}")
    public String deleteUserMovie(@PathVariable Long id) {
        // Find who owned the movie so we can redirect back to their list
        Movie movie = movieRepository.findById(id).orElseThrow();
        Long ownerId = movie.getUser().getId(); // Assuming User ID is Integer
        
        movieRepository.deleteById(id);
        
        return "redirect:/admin/users/" + ownerId + "/movies";
    }
}