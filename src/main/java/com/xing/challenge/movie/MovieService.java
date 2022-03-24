package com.xing.challenge.movie;


import com.xing.challenge.genre.GenreDao;
import com.xing.challenge.movie.dto.ErrorDTO;
import com.xing.challenge.movie.dto.ResponseMovieDTO;
import com.xing.challenge.ports.ArtistInfoService;
import com.xing.challenge.ports.MovieInfoService;
import com.xing.challenge.ports.MovieSearchService;
import com.xing.challenge.ports.dto.InfoMovieDTO;
import com.xing.challenge.ports.dto.MetadataDTO;
import com.xing.challenge.ports.dto.response.ArtistInfoResponseDTO;
import com.xing.challenge.ports.dto.response.MovieInfoResponseDTO;
import com.xing.challenge.ports.dto.response.MovieSearchResponseDTO;
import com.xing.challenge.utils.RequestErrorException;
import com.xing.challenge.utils.RequestUtil;

import java.util.*;
import java.util.stream.Collectors;

public class MovieService {

    private final GenreDao genreDao = new GenreDao();
    private final RequestUtil requestUtil = new RequestUtil();
    private final MovieSearchService movieSearchService = new MovieSearchService(requestUtil);
    private final MovieInfoService movieInfoService = new MovieInfoService(requestUtil);
    private final ArtistInfoService artistInfoService = new ArtistInfoService(requestUtil);
    private final MovieMapper movieMapper = new MovieMapper();

    public ResponseMovieDTO getMoviesByGenre(String genreName, Integer limit, Integer offset) {
        genreDao.getGenreByName(genreName);

        ArtistInfoResponseDTO artistInfos = new ArtistInfoResponseDTO();
        MovieInfoResponseDTO infoMovies = new MovieInfoResponseDTO();
        List<ErrorDTO> errors = new ArrayList<>();
        MetadataDTO metadata = new MetadataDTO();
        try {
            Long revenue = -1000L;
            MovieSearchResponseDTO moviesResponse = movieSearchService.getMoviesIdsByGenre(genreName, revenue, limit, offset);
            Set<Integer> moviesIds = new HashSet<>(moviesResponse.getData());
            metadata = moviesResponse.getMetadata();
            if (moviesIds.isEmpty()) {
                throw new RequestErrorException(450, String.format("No ids available for limit %d", limit));
            }

            infoMovies = movieInfoService.getInfoMoviesByIds(moviesIds, metadata.getTotal());
            Set<Integer> castIds = infoMovies.getData().stream().map(InfoMovieDTO::getCast).flatMap(Collection::stream).
                    collect(Collectors.toSet());

            artistInfos = artistInfoService.getInfoArtistsByIds(castIds, metadata.getTotal());
        } catch (RequestErrorException e) {
            errors.add(new ErrorDTO(e.getStatusCode(), e.getMessage()));
        }

        return movieMapper.mapToMovieResponse(infoMovies.getData(), artistInfos.getData(), errors, metadata);
    }

}
