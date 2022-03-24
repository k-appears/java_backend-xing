package com.xing.challenge.ports;

import com.xing.challenge.ports.dto.response.MovieSearchResponseDTO;
import com.xing.challenge.utils.RequestBatch;
import com.xing.challenge.utils.RequestUtil;

import java.util.List;
import java.util.Map;

import static com.xing.challenge.utils.Constants.STRIDE_MOVIE_SEARCH;
import static com.xing.challenge.utils.Constants.URL_MOVIES_SEARCH;
import static java.lang.Math.min;

public class MovieSearchService {

    private final RequestBatch requestBatch;
    private final RequestUtil requestUtil;


    public MovieSearchService(RequestUtil requestUtil) {
        this.requestUtil = requestUtil;
        this.requestBatch = new RequestBatch(requestUtil);
    }

    public MovieSearchResponseDTO getMoviesIdsByGenre(String genre, Long revenue, Integer limit, Integer offset) {
        int cappedLimit = min(STRIDE_MOVIE_SEARCH + offset, limit - offset);
        Map<String, String> params = Map.of(
                "genre", genre,
                "revenue", revenue.toString(),
                "limit", Integer.toString(cappedLimit),
                "offset", offset.toString());
        MovieSearchResponseDTO movieSearch = requestUtil.request(URL_MOVIES_SEARCH, params, MovieSearchResponseDTO.class);
        int total = movieSearch.getMetadata().getTotal();
        if (limit - offset > STRIDE_MOVIE_SEARCH) {
            List<MovieSearchResponseDTO> batchResponses = requestBatch.getMovieSearches(genre, revenue, cappedLimit, total, movieSearch);
            return batchResponses.stream().reduce(movieSearch, (acc, movieResponse) -> {
                acc.getData().addAll(movieResponse.getData());
                return acc;
            });
        } else {
            return movieSearch;
        }
    }

}