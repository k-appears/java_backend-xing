package com.xing.challenge.movie;

import com.xing.challenge.utils.QueryParameterUtil;
import spark.Request;
import spark.Response;
import spark.Route;


public class MovieController {

    private static final MovieService movieService = new MovieService();

    public static final Route fetchAllMovies = (Request request, Response response) -> {
        String genre = QueryParameterUtil.getQueryGenre(request).orElseThrow(() -> new IllegalArgumentException("Empty genre"));
        Integer limit = QueryParameterUtil.getQueryLimit(request);
        Integer offset = QueryParameterUtil.getQueryOffset(request);

        return movieService.getMoviesByGenre(genre, limit, offset);
    };

    private MovieController() {
    }

}
