package com.xing.challenge.genre;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xing.challenge.genre.model.Genre;

import java.util.List;

public class GenreDao {

    private static final String GENRES_JSON = "[{\"id\": 28, \"name\": \"Action\"}, {\"id\": 12, \"name\": \"Adventure\"}, " +
            "{\"id\": 16, \"name\": \"Animation\"}, {\"id\": 35, \"name\": \"Comedy\"}, {\"id\": 80, \"name\": \"Crime\"}, " +
            "{\"id\": 99, \"name\": \"Documentary\"}, {\"id\": 18, \"name\": \"Drama\"}, {\"id\": 10751, \"name\": \"Family\"}, " +
            "{\"id\": 14, \"name\": \"Fantasy\"}, {\"id\": 36, \"name\": \"History\"}, {\"id\": 27, \"name\": \"Horror\"}, " +
            "{\"id\": 10402, \"name\": \"Music\"}, {\"id\": 9648, \"name\": \"Mystery\"}, {\"id\": 10749, \"name\": \"Romance\"}, " +
            "{\"id\": 878, \"name\": \"Science Fiction\"}, {\"id\": 10770, \"name\": \"TV Movie\"}, {\"id\": 53, \"name\": \"Thriller\"}, " +
            "{\"id\": 10752, \"name\": \"War\"}, {\"id\": 37, \"name\": \"Western\"}]";

    private final List<Genre> genres = new Gson().fromJson(GENRES_JSON, new TypeToken<List<Genre>>() {
    }.getType());

    public Genre getGenreByName(String name) {
        String validName = name.replaceAll("\\P{Print}", "").trim();
        return genres.stream().filter(genre -> genre.getName().equals(validName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Not found genre '%s'", validName)));
    }
}
