package com.xing.challenge.utils;

public class ResponseError {

    private final String message;

    public ResponseError(Exception e) {
        this.message = e.getMessage();
    }


}
