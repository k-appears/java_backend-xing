package com.xing.challenge.ports;

import com.xing.challenge.ports.dto.response.MovieInfoResponseDTO;
import com.xing.challenge.utils.RequestBatch;
import com.xing.challenge.utils.RequestErrorException;
import com.xing.challenge.utils.RequestUtil;

import java.util.List;
import java.util.Set;

public class MovieInfoService {

    private final RequestBatch requestBatch;

    public MovieInfoService(RequestUtil requestUtil) {
        this.requestBatch = new RequestBatch(requestUtil);
    }

    public MovieInfoResponseDTO getInfoMoviesByIds(Set<Integer> ids, Integer total) {
        List<MovieInfoResponseDTO> batchResponses = requestBatch.getMovies(ids, total, MovieInfoResponseDTO.class);
        return batchResponses.stream().reduce((acc, infoResponse) -> {
            acc.getData().addAll(infoResponse.getData());
            return acc;
        }).orElseThrow(() -> new RequestErrorException(450, "No info for movies"));
    }
}
