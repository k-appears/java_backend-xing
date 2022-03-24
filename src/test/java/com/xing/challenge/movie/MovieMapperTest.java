package com.xing.challenge.movie;

import com.xing.challenge.movie.dto.ErrorDTO;
import com.xing.challenge.ports.dto.ArtistInfoDTO;
import com.xing.challenge.ports.dto.InfoMovieDTO;
import com.xing.challenge.ports.dto.MetadataDTO;
import com.xing.challenge.ports.dto.response.ArtistInfoResponseDTO;
import com.xing.challenge.ports.dto.response.MovieInfoResponseDTO;
import com.xing.challenge.utils.JsonUtil;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.xing.challenge.movie.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovieMapperTest {

    @Test
    void mapToMovieResponse_handleException() {
        MovieMapper movieMapper = new MovieMapper();
        List<ArtistInfoDTO> artistInfos = JsonUtil.fromStringToObject(INVALID_ARTIST_INFO_GENRE, ArtistInfoResponseDTO.class).getData();
        List<InfoMovieDTO> infoMovies = JsonUtil.fromStringToObject(MOVIE_INFO, MovieInfoResponseDTO.class).getData();
        List<ErrorDTO> errors = new ArrayList<>();

        movieMapper.mapToMovieResponse(infoMovies, artistInfos, errors, new MetadataDTO());

        assertEquals(3, errors.size());
        assertEquals("Movie id #680 cast info is not complete", errors.get(0).getMessage());
        assertEquals("Movie id #1893 cast info is not complete", errors.get(1).getMessage());
        assertEquals("Movie id #12 cast info is not complete", errors.get(2).getMessage());
    }

    @Test
    void mergeCastInfo_CastIdNotInCastInfo() {
        MovieMapper movieMapper = new MovieMapper();
        List<ArtistInfoDTO> artistInfoDTO = JsonUtil.fromStringToObject(ARTIST_INFO, ArtistInfoResponseDTO.class).getData();
        List<Integer> castsIds = List.of(1);

        IllegalArgumentException aThrows = assertThrows(IllegalArgumentException.class,
                () -> movieMapper.mergeCastInfo(castsIds, artistInfoDTO));
        assertEquals("Not found info for artist 1", aThrows.getMessage());
    }

    @Test
    void mergeCastInfo_InvalidGenre() {
        MovieMapper movieMapper = new MovieMapper();
        List<ArtistInfoDTO> artistInfoDTO = JsonUtil.fromStringToObject(INVALID_ARTIST_INFO_GENRE, ArtistInfoResponseDTO.class).getData();
        List<Integer> castsIds = List.of(3);

        ArrayIndexOutOfBoundsException ex = assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> movieMapper.mergeCastInfo(castsIds, artistInfoDTO));
        assertEquals("Index 9999 out of bounds for length 3", ex.getMessage());
    }

    @Test
    void getYear_InvalidYearFormat() {
        MovieMapper movieMapper = new MovieMapper();

        assertThrows(ParseException.class, () -> movieMapper.getYear("27/04/2022"));
    }
}