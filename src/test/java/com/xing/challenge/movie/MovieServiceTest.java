package com.xing.challenge.movie;

import com.xing.challenge.genre.GenreDao;
import com.xing.challenge.genre.model.Genre;
import com.xing.challenge.movie.dto.ErrorDTO;
import com.xing.challenge.movie.dto.ResponseMovieDTO;
import com.xing.challenge.ports.ArtistInfoService;
import com.xing.challenge.ports.MovieInfoService;
import com.xing.challenge.ports.MovieSearchService;
import com.xing.challenge.ports.dto.MetadataDTO;
import com.xing.challenge.ports.dto.response.ArtistInfoResponseDTO;
import com.xing.challenge.ports.dto.response.MovieInfoResponseDTO;
import com.xing.challenge.ports.dto.response.MovieSearchResponseDTO;
import com.xing.challenge.utils.JsonUtil;
import com.xing.challenge.utils.RequestErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.FieldSetter;

import java.util.List;

import static com.xing.challenge.movie.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    MovieService sut = new MovieService();
    @Mock
    private GenreDao mockGenreDao;
    @Mock
    private MovieSearchService mockMovieSearchService;
    @Mock
    private MovieInfoService mockMovieInfoService;
    @Mock
    private ArtistInfoService mockArtistInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getMoviesByGenre_genreDao_IllegalArgumentException() throws NoSuchFieldException {
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("genreDao"), mockGenreDao);
        when(mockGenreDao.getGenreByName(anyString())).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class, () -> sut.getMoviesByGenre("genreName", 10, 9));
    }

    @Test
    void getMoviesByGenre_mockMovieSearchService_RequestErrorException() throws NoSuchFieldException {
        MovieMapper mockMovieMapper = mock(MovieMapper.class);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("genreDao"), mockGenreDao);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("movieSearchService"), mockMovieSearchService);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("movieMapper"), mockMovieMapper);
        when(mockGenreDao.getGenreByName(anyString())).thenReturn(Mockito.mock(Genre.class));
        when(mockMovieSearchService.getMoviesIdsByGenre(anyString(), anyLong(), anyInt(), anyInt()))
                .thenThrow(new RequestErrorException(500, "error"));
        ArgumentCaptor<List<ErrorDTO>> argCaptor = ArgumentCaptor.forClass(List.class);

        sut.getMoviesByGenre("genreName", 10, 9);

        verify(mockMovieMapper, times(1)).mapToMovieResponse(anyList(), anyList(), argCaptor.capture(), any(MetadataDTO.class));
        assertEquals(1, argCaptor.getValue().size());
        assertEquals("error", argCaptor.getValue().get(0).getMessage());
        assertEquals(500, argCaptor.getValue().get(0).getStatusCode());
    }

    @Test
    void getMoviesByGenre_movieInfoService_RequestErrorException() throws NoSuchFieldException {
        MovieMapper mockMovieMapper = mock(MovieMapper.class);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("genreDao"), mockGenreDao);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("movieSearchService"), mockMovieSearchService);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("movieInfoService"), mockMovieInfoService);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("movieMapper"), mockMovieMapper);
        when(mockGenreDao.getGenreByName(anyString())).thenReturn(Mockito.mock(Genre.class));
        MovieSearchResponseDTO mockMovieSearch = Mockito.mock(MovieSearchResponseDTO.class, Mockito.RETURNS_DEEP_STUBS);
        when(mockMovieSearch.getData()).thenReturn(List.of(1, 2, 3));
        when(mockMovieSearch.getMetadata().getTotal()).thenReturn(1);
        when(mockMovieSearchService.getMoviesIdsByGenre(anyString(), anyLong(), anyInt(), anyInt())).thenReturn(mockMovieSearch);
        when(mockMovieInfoService.getInfoMoviesByIds(anySet(), anyInt()))
                .thenThrow(new RequestErrorException(500, "error getting movies Info"));
        ArgumentCaptor<List<ErrorDTO>> argCaptor = ArgumentCaptor.forClass(List.class);

        sut.getMoviesByGenre("genreName", 10, 9);

        verify(mockMovieMapper, times(1)).mapToMovieResponse(anyList(), anyList(), argCaptor.capture(), any(MetadataDTO.class));
        assertEquals(1, argCaptor.getValue().size());
        assertEquals("error getting movies Info", argCaptor.getValue().get(0).getMessage());
        assertEquals(500, argCaptor.getValue().get(0).getStatusCode());

        verify(mockGenreDao, times(1)).getGenreByName("genreName");
        verify(mockArtistInfoService, never()).getInfoArtistsByIds(anySet(), anyInt());
    }

    @Test
    void getMoviesByGenre_artistInfoService_RequestErrorException() throws NoSuchFieldException {
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("genreDao"), mockGenreDao);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("movieSearchService"), mockMovieSearchService);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("movieInfoService"), mockMovieInfoService);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("artistInfoService"), mockArtistInfoService);
        when(mockGenreDao.getGenreByName(anyString())).thenReturn(Mockito.mock(Genre.class));
        MovieSearchResponseDTO mockMovieSearch = Mockito.mock(MovieSearchResponseDTO.class, Mockito.RETURNS_DEEP_STUBS);
        when(mockMovieSearch.getData()).thenReturn(List.of(1, 2, 3));
        when(mockMovieSearch.getMetadata().getTotal()).thenReturn(1);
        when(mockMovieSearchService.getMoviesIdsByGenre(anyString(), anyLong(), anyInt(), anyInt())).thenReturn(mockMovieSearch);
        when(mockMovieInfoService.getInfoMoviesByIds(anySet(), anyInt()))
                .thenReturn(Mockito.mock(MovieInfoResponseDTO.class));
        when(mockArtistInfoService.getInfoArtistsByIds(anySet(), anyInt()))
                .thenThrow(new RequestErrorException(500, "error getting artist"));

        ResponseMovieDTO result = sut.getMoviesByGenre("genreName", 10, 9);

        assertEquals(1, result.getErrors().size());
        assertEquals("error getting artist", result.getErrors().get(0).getMessage());
        assertEquals(500, result.getErrors().get(0).getStatusCode());
    }


    @Test
    void getMoviesByGenre() throws NoSuchFieldException {
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("genreDao"), mockGenreDao);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("movieSearchService"), mockMovieSearchService);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("movieInfoService"), mockMovieInfoService);
        FieldSetter.setField(sut, sut.getClass().getDeclaredField("artistInfoService"), mockArtistInfoService);

        MovieSearchResponseDTO movieSearchDTO = JsonUtil.fromStringToObject(MOVIE_SEARCH, MovieSearchResponseDTO.class);
        MovieInfoResponseDTO movieInfoDTO = JsonUtil.fromStringToObject(MOVIE_INFO, MovieInfoResponseDTO.class);
        ArtistInfoResponseDTO artistInfoDTO = JsonUtil.fromStringToObject(ARTIST_INFO, ArtistInfoResponseDTO.class);

        when(mockGenreDao.getGenreByName(anyString())).thenReturn(Mockito.mock(Genre.class));
        when(mockMovieSearchService.getMoviesIdsByGenre(anyString(), anyLong(), anyInt(), anyInt())).thenReturn(movieSearchDTO);
        when(mockMovieInfoService.getInfoMoviesByIds(anySet(), anyInt())).thenReturn(movieInfoDTO);
        when(mockArtistInfoService.getInfoArtistsByIds(anySet(), anyInt())).thenReturn(artistInfoDTO);

        ResponseMovieDTO result = sut.getMoviesByGenre("genreName", 10, 9);

        assertEquals(3, result.getData().getMovies().size());
    }
}