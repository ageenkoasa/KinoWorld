package com.example.kino.controller;

import com.example.kino.dto.UserDto;
import com.example.kino.exception.MainException;
import com.example.kino.model.Movie;
import com.example.kino.repository.MovieRepository;
import com.example.kino.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MovieController {
    private final MovieService movieService;
    private final MovieRepository movieRepo;

    public MovieController(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepo = movieRepository;
    }

    /**
     * Выводим на экран список всех фильмов
     */
    @GetMapping("/catalog")
    public String getAllMovies(Model model) {
        try {
            model.addAttribute("movies", movieService.allMovies());
            return "catalog";
        } catch (MainException e) {
            return "redirect:/error_500";
        }
    }

    /**
     * Переходим к самому фильму для просмотра
     */
    @GetMapping("/catalog/{id}")
    public String getMovie(@PathVariable(value = "id") Long id, Model model, HttpServletRequest request) {
        if (!movieRepo.existsById(id)) {
            return "redirect:/catalog";
        }

        if(!isAuthorized(request)) {
            return "redirect:/login";
        }

        Optional<Movie> movie = movieRepo.findById(id);
        ArrayList<Movie> res = new ArrayList<>();
        movie.ifPresent(res::add);
        model.addAttribute("movie", res);
        return "watch";
    }

    /**
     * Добавляем  новый фильм
     */
    @GetMapping("/catalog/add")
    public String addMovie(Model model) {
        boolean isAuthorized = true;
        model.addAttribute("isAuthorized", isAuthorized);

        return "add";
    }

    @PostMapping("/catalog/add")
    public String postMovie(@RequestParam String title, @RequestParam Integer release_year, @RequestParam String country, @RequestParam String director, @RequestParam String genre, @RequestParam Double rating, @RequestParam Integer duration_minutes, @RequestParam String description, Model model) {
        Movie movie = new Movie(title, release_year, country, director, genre, rating, duration_minutes, description);
        movieRepo.save(movie);

        return "redirect:/catalog";
    }

    /**
     * Ищем фильм по названию
     */
    @GetMapping("/search")
    public String search(Model model) {
        return "search";
    }

    @PostMapping("/search")
    public String searchMovies(@RequestParam(value = "title") String title, Model model) {
        Optional<Movie> movieByTitle = movieService.searchByTitle(title);

        if (movieByTitle.isEmpty()) {
            return "redirect:/search";
        }

        Movie movie = movieByTitle.get();
        Long id = movie.getId();

        return "redirect:/catalog/" + id;
    }

    /**
     * Редактируем фильм
     *
     */
    @GetMapping("/catalog/{id}/edit")
    public String editMovie(@PathVariable(value = "id") Long id, Model model) {
        if (!movieRepo.existsById(id)) {
            return "redirect:/catalog";
        }
        Optional<Movie> movie = movieRepo.findById(id);
        ArrayList<Movie> res = new ArrayList<>();
        movie.ifPresent(res::add);
        model.addAttribute("movie", res);
        return "edit";
    }

    @PostMapping("/catalog/{id}/edit")
    public String updatedMovie(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam Integer release_year, @RequestParam String country, @RequestParam String director, @RequestParam String genre, @RequestParam Double rating, @RequestParam Integer duration_minutes, @RequestParam String description, Model model) {
        Movie movie = movieRepo.findById(id).orElseThrow();
        movie.setCountry(title);
        movie.setRelease_year(release_year);
        movie.setCountry(country);
        movie.setDirector(director);
        movie.setGenre(genre);
        movie.setRating(rating);
        movie.setDuration_minutes(duration_minutes);
        movie.setDescription(description);
        movieRepo.save(movie);

        return "redirect:/catalog/" + id;
    }

    @PostMapping("/catalog/{id}/remove")
    public String deleteMovie(@PathVariable(value = "id") Long id, Model model) {
        Movie movie = movieRepo.findById(id).orElseThrow();
        movieRepo.delete(movie);

        return "redirect:/catalog";
    }

    @ModelAttribute("isAuthorized")
    public boolean isAuthorized(HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        return user != null;
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(HttpServletRequest request) {
        UserDto user = (UserDto) request.getSession().getAttribute("user");
        return user != null && user.hasRole("ADMIN");
    }
}
