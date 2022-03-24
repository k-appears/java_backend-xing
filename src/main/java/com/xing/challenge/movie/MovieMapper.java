package com.xing.challenge.movie;

import com.xing.challenge.movie.dto.*;
import com.xing.challenge.ports.dto.ArtistInfoDTO;
import com.xing.challenge.ports.dto.InfoMovieDTO;
import com.xing.challenge.ports.dto.MetadataDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MovieMapper {

    public ResponseMovieDTO mapToMovieResponse(List<InfoMovieDTO> infoMovies, List<ArtistInfoDTO> infoArtists,
                                               List<ErrorDTO> errors, MetadataDTO metadata) {

        List<MovieDTO> movies = infoMovies.stream().map(info -> {
            List<CastDTO> casts = List.of();
            Integer releaseYear = 0;
            try {
                releaseYear = getYear(info.getReleaseDate());
                casts = mergeCastInfo(info.getCast(), infoArtists);
            } catch (ParseException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                errors.add(new ErrorDTO(440, String.format("Movie id #%d cast info is not complete", info.getId())));
            }
            return new MovieDTO(info.getId().toString(),
                    info.getTitle(),
                    releaseYear,
                    info.getRevenue().toString(),
                    info.getPosterPath(),
                    info.getGenres().stream().map(Object::toString).collect(Collectors.toList()),
                    casts);

        }).collect(Collectors.toList());

        DataDTO data = new DataDTO(movies);
        return new ResponseMovieDTO(data, errors, metadata);
    }

    List<CastDTO> mergeCastInfo(List<Integer> castIds, List<ArtistInfoDTO> infoArtists) {
        Map<String, ArtistInfoDTO> mapIdArtistInfo = infoArtists.stream().collect(Collectors.toMap(ArtistInfoDTO::getId, Function.identity()));
        return castIds.stream().filter(Objects::nonNull).map(id -> {
            ArtistInfoDTO artistInfo = Optional.ofNullable(mapIdArtistInfo.get(id.toString()))
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Not found info for artist %d", id)));
            String gender = getGender(artistInfo.getGender());
            return new CastDTO(id.toString(), gender, artistInfo.getName(), artistInfo.getProfilePath());
        }).collect(Collectors.toList());
    }

    private String getGender(Integer artistInfo) {
        String name = Gender.values()[artistInfo].name().toLowerCase(); // Throws IllegalArgumentException
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    Integer getYear(String releaseDate) throws ParseException {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        Date date = parser.parse(releaseDate);
        return Integer.parseInt(df.format(date));
    }

    private enum Gender {
        NON_BINARY,
        FEMALE,
        MALE
    }
}
