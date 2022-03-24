package com.xing.challenge;

import com.xing.challenge.movie.MovieController;
import com.xing.challenge.utils.ResponseError;

import static com.xing.challenge.utils.JsonUtil.json;
import static com.xing.challenge.utils.JsonUtil.toJson;
import static spark.Spark.exception;
import static spark.Spark.get;

public class Application {
    public static void main(String[] args) {
        get("/movies", MovieController.fetchAllMovies, json());

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });
    }

}
