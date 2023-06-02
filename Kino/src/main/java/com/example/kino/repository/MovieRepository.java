package com.example.kino.repository;

import com.example.kino.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAll();
    Optional<Movie> findById(Long id);
    Optional<Movie> findMovieByTitle(String title);
}
