package com.xing.challenge.ports.dto.response;

import com.xing.challenge.ports.dto.InfoMovieDTO;

import java.util.ArrayList;
import java.util.List;

public class MovieInfoResponseDTO extends ResponseDTO {
    private final List<InfoMovieDTO> data = new ArrayList<>();

    public List<InfoMovieDTO> getData() {
        return data;
    }
}
