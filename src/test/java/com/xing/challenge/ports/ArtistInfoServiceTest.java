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

class ArtistInfoServiceTest {


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getArtistInfoByIds_emptyIds() {
        ArtistInfoService ArtistInfoService = new ArtistInfoService(Mockito.mock(RequestUtil.class));

        Set<Integer> emptySet = Set.of();
        RequestErrorException aThrows = assertThrows(RequestErrorException.class,
                () -> ArtistInfoService.getInfoArtistsByIds(emptySet, 10));
        assertEquals("No info for artist", aThrows.getMessage());
        assertEquals(450, aThrows.getStatusCode());
    }
}