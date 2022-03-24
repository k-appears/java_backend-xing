package com.xing.challenge.utils;

import spark.Request;

import java.util.Optional;

public class QueryParameterUtil {

    public static final String DEFAULT_LIMIT_VALUE = "10";

    private QueryParameterUtil() {
    }

    public static Optional<String> getQueryGenre(Request request) {
        return Optional.ofNullable(request.queryParams("genre"));
    }

    public static Integer getQueryOffset(Request request) {
        return Integer.parseInt(request.queryParamOrDefault("offset", "0"));
    }

    public static Integer getQueryLimit(Request request) {
        return Integer.parseInt(request.queryParamOrDefault("limit", DEFAULT_LIMIT_VALUE));
    }
}
