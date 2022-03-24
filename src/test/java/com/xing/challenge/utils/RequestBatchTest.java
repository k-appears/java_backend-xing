package com.xing.challenge.utils;

import com.xing.challenge.ports.dto.response.ArtistInfoResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RequestBatchTest {


    @Captor
    private ArgumentCaptor<Map<String, String>> argCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getResponseDTOS_emptyIds() {
        RequestBatch requestBatch = new RequestBatch(Mockito.mock(RequestUtil.class));

        List<ArtistInfoResponseDTO> responseDTOS = requestBatch.getResponseDTOS(Set.of(), 10, ArtistInfoResponseDTO.class);
        assertEquals(0, responseDTOS.size());
    }


    @Test
    void getResponseDTOS_totalGraterThanIdsLowerThanLimit() {
        RequestUtil requestUtil = Mockito.mock(RequestUtil.class);
        when(requestUtil.request(anyString(), anyMap(), Mockito.any())).thenReturn(new ArtistInfoResponseDTO());
        RequestBatch RequestBatch = new RequestBatch(requestUtil);


        RequestBatch.getResponseDTOS(new HashSet<>(List.of(1, 2, 3, 4)), 10, ArtistInfoResponseDTO.class);

        verify(requestUtil, times(1)).request(anyString(), argCaptor.capture(), Mockito.any());
        assertEquals(Map.of("ids", "1,2,3,4"), argCaptor.getValue());
    }

    @Test
    void getResponseDTOS_totalLowerThanIdsLowerThanLimit() {

        RequestUtil requestUtil = Mockito.mock(RequestUtil.class);
        when(requestUtil.request(anyString(), anyMap(), Mockito.any())).thenReturn(new ArtistInfoResponseDTO());
        RequestBatch RequestBatch = new RequestBatch(requestUtil);

        RequestBatch.getResponseDTOS(new HashSet<>(List.of(1, 2, 3, 4)), 1, ArtistInfoResponseDTO.class);

        verify(requestUtil, times(1)).request(anyString(), argCaptor.capture(), Mockito.any());
        assertEquals(Map.of("ids", "1"), argCaptor.getValue());
    }

    @Test
    void getResponseDTOS_totalGreaterThanIdsHigherThanLimit() {
        RequestUtil requestUtil = Mockito.mock(RequestUtil.class);
        when(requestUtil.request(anyString(), anyMap(), Mockito.any())).thenReturn(new ArtistInfoResponseDTO());
        RequestBatch RequestBatch = new RequestBatch(requestUtil);

        RequestBatch.getResponseDTOS(new HashSet<>(List.of(1, 2, 3, 4, 5, 6)), 10, ArtistInfoResponseDTO.class);

        verify(requestUtil, times(2)).request(anyString(), argCaptor.capture(), Mockito.any());
        assertEquals(Map.of("ids", "1,2,3,4,5"), argCaptor.getAllValues().get(0));
        assertEquals(Map.of("ids", "6"), argCaptor.getAllValues().get(1));
    }

    @Test
    void getResponseDTOS_totalLowerThanIdsHigherThanLimit() {
        RequestUtil requestUtil = Mockito.mock(RequestUtil.class);
        when(requestUtil.request(anyString(), anyMap(), Mockito.any())).thenReturn(new ArtistInfoResponseDTO());
        RequestBatch RequestBatch = new RequestBatch(requestUtil);

        RequestBatch.getResponseDTOS(new HashSet<>(List.of(1, 2, 3, 4, 5, 6)), 1, ArtistInfoResponseDTO.class);

        verify(requestUtil, times(1)).request(anyString(), argCaptor.capture(), Mockito.any());
        assertEquals(Map.of("ids", "1"), argCaptor.getValue());
    }
}