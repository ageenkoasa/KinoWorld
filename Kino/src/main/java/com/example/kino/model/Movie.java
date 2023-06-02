package com.example.kino.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "movies")
@Table(
        name = "movies",
        uniqueConstraints = {
                @UniqueConstraint(name = "movie_title_unique", columnNames = "title")
        }
)
public class Movie {
    @Id
    @SequenceGenerator(
            name = "movie_sequence",
            sequenceName = "movie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_sequence"
    )
    @Column(
            name = "movie_id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "title",
            nullable = false
    )
    private String title;

    @Column(
            name = "country"
    )
    private String country;

    @Column(
            name = "director"
    )
    private String director;

    @Column(
            name = "genre"
    )
    private String genre;

    @Column(
            name = "description"
    )
    private String description;

    @Column(
            name = "release_year"
    )
    private Integer release_year;

    @Column(
            name = "rating"
    )
    private Double rating;

    @Column(
            name = "duration_minutes"
    )
    private Integer duration_minutes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Movie() {

    }

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Movie(String title, Integer release_year, String country, String director, String genre, Double rating, Integer duration_minutes, String description) {
        this.title = title;
        this.country = country;
        this.release_year = release_year;
        this.director = director;
        this.genre = genre;
        this.rating = rating;
        this.duration_minutes  = duration_minutes;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRelease_year() {
        return release_year;
    }

    public void setRelease_year(Integer release_year) {
        this.release_year = release_year;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getDuration_minutes() {
        return duration_minutes;
    }

    public void setDuration_minutes(Integer duration_minutes) {
        this.duration_minutes = duration_minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (release_year != movie.release_year) return false;
        if (rating != movie.rating) return false;
        if (duration_minutes != movie.duration_minutes) return false;
        if (!Objects.equals(id, movie.id)) return false;
        if (!Objects.equals(title, movie.title)) return false;
        if (!Objects.equals(country, movie.country)) return false;
        if (!Objects.equals(director, movie.director)) return false;
        if (!Objects.equals(genre, movie.genre)) return false;
        return Objects.equals(description, movie.description);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (director != null ? director.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (release_year != null ? release_year.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (duration_minutes != null ? duration_minutes.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", country='" + country + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", release_year=" + release_year +
                ", rating=" + rating +
                ", duration_minutes=" + duration_minutes +
                '}';
    }
}
