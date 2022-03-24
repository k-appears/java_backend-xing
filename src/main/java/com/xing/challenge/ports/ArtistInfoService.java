package com.xing.challenge.ports;

import com.xing.challenge.ports.dto.response.ArtistInfoResponseDTO;
import com.xing.challenge.utils.RequestBatch;
import com.xing.challenge.utils.RequestErrorException;
import com.xing.challenge.utils.RequestUtil;

import java.util.List;
import java.util.Set;

public class ArtistInfoService extends ArtistInfoResponseDTO {

    private final RequestBatch requestBatch;

    public ArtistInfoService(RequestUtil requestUtil) {
        this.requestBatch = new RequestBatch(requestUtil);
    }

    public ArtistInfoResponseDTO getInfoArtistsByIds(Set<Integer> uniqueIds, Integer total) {
        List<ArtistInfoResponseDTO> batchResponses = requestBatch.getArtistInfo(uniqueIds, total, ArtistInfoResponseDTO.class);
        return batchResponses.stream().reduce((acc, artistResponse) -> {
            acc.getData().addAll(artistResponse.getData());
            return acc;
        }).orElseThrow(() -> new RequestErrorException(450, "No info for artist"));
    }
}
