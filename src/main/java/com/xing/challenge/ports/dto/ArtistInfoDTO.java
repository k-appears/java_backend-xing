package com.xing.challenge.ports.dto;

import java.util.List;

public class ArtistInfoDTO {
    private String name;
    private Integer gender;
    private String profilePath;
    private List<Integer> movies;
    private String id;

    public String getName() {
        return name;
    }

    public Integer getGender() {
        return gender;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public List<Integer> getMovies() {
        return movies;
    }

    public String getId() {
        return id;
    }
}
