package com.xing.challenge.genre;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GenreDaoTest {

    @Test
    void getGenreByName_invalidName_IllegalArgumentException() {
        String genre = "qualsevol_genre";
        GenreDao genreDao = new GenreDao();
        IllegalArgumentException aThrows = assertThrows(IllegalArgumentException.class, () -> genreDao.getGenreByName(genre));
        assertEquals("Not found genre 'qualsevol_genre'", aThrows.getMessage());
    }

    @Test
    void getGenreByName_emptyName_IllegalArgumentException() {
        String genre = "   ";
        GenreDao genreDao = new GenreDao();
        IllegalArgumentException aThrows = assertThrows(IllegalArgumentException.class, () -> genreDao.getGenreByName(genre));
        assertEquals("Not found genre ''", aThrows.getMessage());
    }

    @Test
    void getGenreByName() {
        String genre = "Action";
        GenreDao genreDao = new GenreDao();
        assertEquals(28, genreDao.getGenreByName(genre).getId());
    }
}