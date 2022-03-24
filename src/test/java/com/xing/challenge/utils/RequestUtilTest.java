package com.xing.challenge.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import spark.Request;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


class RequestUtilTest {

    @Spy
    private Request spyRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getQueryGenre_NotInQueryParam() {
        Mockito.doReturn(null).when(spyRequest).queryParams("genre");
        Optional<String> genre = QueryParameterUtil.getQueryGenre(spyRequest);
        assertFalse(genre.isPresent());
    }


    @Test
    void getQueryOffset_IllegalArgumentException() {
        Mockito.doReturn("not_a_number").when(spyRequest).queryParams("offset");
        IllegalArgumentException aThrows = assertThrows(IllegalArgumentException.class, () -> QueryParameterUtil.getQueryOffset(spyRequest));
        assertEquals("For input string: \"not_a_number\"", aThrows.getMessage());
    }

    @Test
    void getQueryOffset_NotInQueryParam() {
        Mockito.doReturn(null).when(spyRequest).queryParams("offset");
        Integer offset = QueryParameterUtil.getQueryOffset(spyRequest);
        assertEquals(0, offset);
    }

    @Test
    void getQueryOffset() {
        Mockito.doReturn("10").when(spyRequest).queryParams("offset");
        Integer offset = QueryParameterUtil.getQueryOffset(spyRequest);
        assertEquals(10, offset);
    }


    @Test
    void getQueryLimit_IllegalArgumentException() {
        Mockito.doReturn("not_a_number").when(spyRequest).queryParams("limit");
        IllegalArgumentException aThrows = assertThrows(IllegalArgumentException.class, () -> QueryParameterUtil.getQueryLimit(spyRequest));
        assertEquals("For input string: \"not_a_number\"", aThrows.getMessage());
    }

    @Test
    void getQueryLimit_NotInQueryParam() {
        Mockito.doReturn(null).when(spyRequest).queryParams("limit");
        Integer offset = QueryParameterUtil.getQueryLimit(spyRequest);
        assertEquals(10, offset);
    }

    @Test
    void getQueryLimit() {
        Mockito.doReturn("10").when(spyRequest).queryParams("limit");
        Integer offset = QueryParameterUtil.getQueryLimit(spyRequest);
        assertEquals(10, offset);
    }
}