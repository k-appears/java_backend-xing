package com.xing.challenge.ports;

import com.xing.challenge.utils.RequestErrorException;
import com.xing.challenge.utils.RequestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovieInfoServiceTest {


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getInfoMoviesByIds_emptyIds() {
        MovieInfoService movieInfoService = new MovieInfoService(Mockito.mock(RequestUtil.class));

        Set<Integer> emptySet = Set.of();
        RequestErrorException aThrows = assertThrows(RequestErrorException.class,
                () -> movieInfoService.getInfoMoviesByIds(emptySet, 10));
        assertEquals("No info for movies", aThrows.getMessage());
        assertEquals(450, aThrows.getStatusCode());
    }
}