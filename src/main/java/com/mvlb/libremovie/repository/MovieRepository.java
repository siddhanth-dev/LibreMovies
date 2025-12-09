package com.mvlb.libremovie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mvlb.libremovie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

	List<Movie> findByTitleContainingIgnoreCase(String keyword);
}
