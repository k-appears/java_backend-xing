package com.xing.challenge.ports.dto;

import java.util.List;

public class InfoMovieDTO {
    private Integer id;
    private String title;
    private String tagline;

    private String overview;
    private Double popularity;
    private Integer runtime;
    private String releaseDate;
    private Integer revenue;
    private Integer budget;
    private String posterPath;
    private String originalLanguage;
    private List<Integer> genres;
    private List<Integer> cast;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTagline() {
        return tagline;
    }

    public String getOverview() {
        return overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public Integer getBudget() {
        return budget;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public List<Integer> getCast() {
        return cast;
    }
}
