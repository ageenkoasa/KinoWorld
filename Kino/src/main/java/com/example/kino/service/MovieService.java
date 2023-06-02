package com.example.kino.service;

import com.example.kino.model.Movie;
import com.example.kino.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepo;

    public MovieService(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    public List<Movie> allMovies() {
        return movieRepo.findAll();
    }

    public Optional<Movie> searchByTitle(String keyword) {
        return movieRepo.findMovieByTitle(keyword);
    }

}

