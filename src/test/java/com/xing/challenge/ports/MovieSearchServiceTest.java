package com.xing.challenge.ports;

import com.xing.challenge.ports.dto.response.MovieSearchResponseDTO;
import com.xing.challenge.utils.RequestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MovieSearchServiceTest {

    @Captor
    private ArgumentCaptor<Map<String, String>> argCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @ParameterizedTest
    @CsvSource({"0, 100, 85, 1", "1, 102, 85, 1", "101, 102, 85, 1", "0, 103, 204, 3"})
    void getMoviesIdsByGenre(Integer offset, Integer limit, Integer total, Integer timesCalled) {
        RequestUtil requestUtil = Mockito.mock(RequestUtil.class);
        MovieSearchResponseDTO mockMovieSearchResponse = Mockito.mock(MovieSearchResponseDTO.class, Mockito.RETURNS_DEEP_STUBS);
        when(mockMovieSearchResponse.getMetadata().getTotal()).thenReturn(total);
        when(requestUtil.request(anyString(), anyMap(), Mockito.any())).thenReturn(mockMovieSearchResponse);
        MovieSearchService movieSearchService = new MovieSearchService(requestUtil);

        movieSearchService.getMoviesIdsByGenre("Action", -1L, limit, offset);

        verify(requestUtil, times(timesCalled)).request(anyString(), argCaptor.capture(), Mockito.any());
        assertEquals(Map.of("genre", "Action",
                "revenue", "-1",
                "limit", String.valueOf(min(100 + offset, limit - offset)),
                "offset", offset.toString()), argCaptor.getAllValues().get(0));
    }
}