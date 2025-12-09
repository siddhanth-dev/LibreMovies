package com.mvlb.libremovie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;                // ✔ Correct import
import org.springframework.data.domain.Pageable;         // ✔ Correct import
import org.springframework.data.domain.PageRequest;      // ✔ Correct import
import org.springframework.stereotype.Service;

import com.mvlb.libremovie.entity.Movie;
import com.mvlb.libremovie.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repo;

    // Pagination method
    public Page<Movie> getPaginatedMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);   
        return repo.findAll(pageable);                        
    }

    public Movie saveMovie(Movie movie) {
        return repo.save(movie);
    }

    public Movie getMovieById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteMovie(Integer id) {
        repo.deleteById(id);     
    }
}
