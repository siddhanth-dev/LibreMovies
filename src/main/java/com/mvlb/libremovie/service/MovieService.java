package com.mvlb.libremovie.service;

import com.mvlb.libremovie.entity.Movie;
import com.mvlb.libremovie.entity.User;
import com.mvlb.libremovie.repository.MovieRepository;
import com.mvlb.libremovie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private UserRepository userRepository;

    // 1. Get Movies (Only for the logged-in user)
    public Page<Movie> getPaginatedMovies(int page, int size, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        // Page - 1 because database pages start at 0, but your URL starts at 1
        return movieRepository.findByUser(user, PageRequest.of(page - 1, size));
    }

    // 2. Search Movies (Only for the logged-in user)
    public List<Movie> searchMovies(String keyword, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        return movieRepository.findByUserAndTitleContainingIgnoreCase(user, keyword);
    }

    // 3. Save Movie (Attach the user before saving)
    public void save(Movie movie, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        movie.setUser(user); // 🔗 This links the movie to the specific user
        movieRepository.save(movie);
    }
    
    // 4. Get Single Movie
    public Movie getMovieById(Long id) {
        Optional<Movie> optional = movieRepository.findById(id);
        return optional.orElse(null);
    }

    // 5. Delete Movie
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }
}