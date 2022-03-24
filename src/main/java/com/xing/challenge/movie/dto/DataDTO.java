package com.xing.challenge.movie.dto;

import java.util.List;

public class DataDTO {
    private final List<MovieDTO> movies;

    public DataDTO(List<MovieDTO> movies) {
        this.movies = movies;
    }

    public List<MovieDTO> getMovies() {
        return movies;
    }
}
