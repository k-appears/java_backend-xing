package com.xing.challenge.movie.dto;

import com.xing.challenge.ports.dto.MetadataDTO;

import java.util.List;

public class ResponseMovieDTO {

    private final DataDTO data;
    private final List<ErrorDTO> errors;
    private final MetadataDTO metadata;


    public ResponseMovieDTO(DataDTO data, List<ErrorDTO> errors, MetadataDTO metadata) {
        this.data = data;
        this.errors = errors;
        this.metadata = metadata;
    }

    public DataDTO getData() {
        return data;
    }

    public List<ErrorDTO> getErrors() {
        return errors;
    }

    public MetadataDTO getMetadata() {
        return metadata;
    }
}
