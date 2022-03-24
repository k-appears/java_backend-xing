package com.xing.challenge.ports.dto.response;

import java.util.List;

public class MovieSearchResponseDTO extends ResponseDTO {
    private List<Integer> data;

    public List<Integer> getData() {
        return data;
    }
}
