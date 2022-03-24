package com.xing.challenge.movie;

import com.xing.challenge.genre.GenreDao;
import com.xing.challenge.genre.model.Genre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class GenreDaoTest {

    @Test
    void test_NotFound_getGenreByName() {
        GenreDao genreDao = new GenreDao();
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> genreDao.getGenreByName("Invalid Genre"));
        assertEquals("Not found genre 'Invalid Genre'", iae.getMessage());
    }

    @Test
    void test_getGenreByName() {
        GenreDao genreDao = new GenreDao();
        Genre genre = genreDao.getGenreByName("Action");
        assertNotNull(genre);
        assertEquals(28, genre.getId());
    }
}