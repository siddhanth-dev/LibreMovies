package com.mvlb.libremovie.repository;

import com.mvlb.libremovie.entity.Movie;
import com.mvlb.libremovie.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByUser(User user, Pageable pageable);

    List<Movie> findByUserAndTitleContainingIgnoreCase(User user, String title);
    
}