package com.xing.challenge.ports.dto.response;

import com.xing.challenge.ports.dto.ArtistInfoDTO;

import java.util.ArrayList;
import java.util.List;

public class ArtistInfoResponseDTO extends ResponseDTO {

    private final List<ArtistInfoDTO> data = new ArrayList<>();

    public List<ArtistInfoDTO> getData() {
        return data;
    }
}
