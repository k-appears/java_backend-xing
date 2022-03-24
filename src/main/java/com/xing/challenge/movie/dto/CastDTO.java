package com.xing.challenge.movie.dto;

public class CastDTO {
    private final String id;
    private final String gender;
    private final String name;
    private final String profilePath;

    public CastDTO(String id, String gender, String name, String profilePath) {
        this.id = id;
        this.gender = gender;
        this.name = name;
        this.profilePath = profilePath;
    }
}
