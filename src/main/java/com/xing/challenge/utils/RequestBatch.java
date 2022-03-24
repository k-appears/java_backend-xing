package com.xing.challenge.utils;

import com.xing.challenge.ports.dto.response.ArtistInfoResponseDTO;
import com.xing.challenge.ports.dto.response.MovieInfoResponseDTO;
import com.xing.challenge.ports.dto.response.MovieSearchResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static com.xing.challenge.utils.Constants.*;
import static java.lang.Math.min;
import static java.util.stream.Collectors.joining;

public class RequestBatch {
    private final RequestUtil requestUtil;


    public RequestBatch(RequestUtil requestUtil) {
        this.requestUtil = requestUtil;
    }

    public List<MovieInfoResponseDTO> getMovies(Set<Integer> uniqueIds, Integer total, Class<MovieInfoResponseDTO> clazz) {
        return getResponseDTOS(uniqueIds, total, clazz);
    }

    public List<ArtistInfoResponseDTO> getArtistInfo(Set<Integer> uniqueIds, Integer total, Class<ArtistInfoResponseDTO> clazz) {
        return getResponseDTOS(uniqueIds, total, clazz);
    }

    public List<MovieSearchResponseDTO> getMovieSearches(String genre, Long revenue, Integer offset, Integer total,
                                                         MovieSearchResponseDTO movieSearch) {
        Map<String, String> params;
        int start = offset;
        int end = min(start + STRIDE_MOVIE_SEARCH, total);
        List<MovieSearchResponseDTO> batchResponses = new ArrayList<>(List.of(movieSearch));
        while (start < end) {
            params = Map.of("genre", genre, "revenue", revenue.toString(), "limit", String.valueOf(end),
                    "offset", String.valueOf(start));
            batchResponses.add(requestUtil.request(URL_MOVIES, params, MovieSearchResponseDTO.class));
            start = end;
            end = min(start + STRIDE_MOVIE_SEARCH, total);
        }
        return batchResponses;
    }

    <T> List<T> getResponseDTOS(Set<Integer> uniqueIds, Integer total, Class<T> responseDTOClass) {
        List<T> batchResponses = new ArrayList<>();
        int start = 0;
        List<Integer> ids = new ArrayList<>(uniqueIds);
        int end = min(STRIDE, min(ids.size(), total));
        while (start < end) {
            String joinIds = IntStream.range(start, end)
                    .mapToObj(ids::get)
                    .map(Object::toString)
                    .collect(joining(","));
            Map<String, String> params = Map.of("ids", joinIds);
            batchResponses.add(requestUtil.request(URL_ARTISTS, params, responseDTOClass));
            start += end;
            end = min(start + STRIDE, min(ids.size(), total));
        }
        return batchResponses;
    }
}