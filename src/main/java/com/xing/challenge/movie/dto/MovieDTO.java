package com.xing.challenge.movie.dto;

import java.util.List;

public class MovieDTO {
    private final String id;
    private final String title;
    private final Integer releaseYear;
    private final String revenue;
    private final String posterPath;
    private final List<String> genres;
    private final List<CastDTO> casts;

    public MovieDTO(String id, String title, Integer releaseYear, String revenue, String posterPath, List<String> genres, List<CastDTO> casts) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.revenue = revenue;
        this.posterPath = posterPath;
        this.genres = genres;
        this.casts = casts;
    }
}
