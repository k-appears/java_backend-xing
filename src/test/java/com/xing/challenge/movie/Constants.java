package com.xing.challenge.movie;

public class Constants {
    public static final String ARTIST_INFO = "{\n" +
            "    \"metadata\": {\n" +
            "        \"offset\": 0,\n" +
            "        \"limit\": 2,\n" +
            "        \"total\": 2\n" +
            "    },\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"name\": \"Mark Hamill\",\n" +
            "            \"gender\": 2,\n" +
            "            \"profilePath\": \"https://image.tmdb.org/t/p/w185/fk8OfdReNltKZqOk2TZgkofCUFq.jpg\",\n" +
            "            \"movies\": [\n" +
            "                11,\n" +
            "                181808\n" +
            "            ],\n" +
            "            \"id\": \"2\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"name\": \"Harrison Ford\",\n" +
            "            \"gender\": 2,\n" +
            "            \"profilePath\": \"https://image.tmdb.org/t/p/w185/7LOTdRfHU1H1qHBxpUv3jT04eWB.jpg\",\n" +
            "            \"movies\": [\n" +
            "                78,\n" +
            "                335984,\n" +
            "                140607,\n" +
            "                11\n" +
            "            ],\n" +
            "            \"id\": \"3\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";


    public static final String MOVIE_INFO = "{\n" +
            "\"metadata\": {\n" +
            "        \"offset\": 0,\n" +
            "        \"limit\": 3,\n" +
            "        \"total\": 3\n" +
            "    },\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"id\": 680,\n" +
            "            \"title\": \"Pulp Fiction\",\n" +
            "            \"tagline\": \"Just because you are a character doesn't mean you have character.\",\n" +
            "            \"overview\": \"A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.\",\n" +
            "            \"popularity\": 22.685188,\n" +
            "            \"runtime\": 154,\n" +
            "            \"releaseDate\": \"1994-09-10\",\n" +
            "            \"revenue\": 213928762,\n" +
            "            \"budget\": 8000000,\n" +
            "            \"posterPath\": \"https://image.tmdb.org/t/p/w342/dM2w364MScsjFf8pfMbaWUcWrR.jpg\",\n" +
            "            \"originalLanguage\": \"en\",\n" +
            "            \"genres\": [\n" +
            "                53,\n" +
            "                80\n" +
            "            ],\n" +
            "            \"cast\": [\n" +
            "                2,\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 1893,\n" +
            "            \"title\": \"Star Wars: Episode I - The Phantom Menace\",\n" +
            "            \"tagline\": \"Every generation has a legend. Every journey has a first step. Every saga has a beginning.\",\n" +
            "            \"overview\": \"Anakin Skywalker, a young slave strong with the Force, is discovered on Tatooine. Meanwhile, the evil Sith have returned, enacting their plot for revenge against the Jedi.\",\n" +
            "            \"popularity\": 22.71543,\n" +
            "            \"runtime\": 136,\n" +
            "            \"releaseDate\": \"1999-05-19\",\n" +
            "            \"revenue\": 924317558,\n" +
            "            \"budget\": 115000000,\n" +
            "            \"posterPath\": \"https://image.tmdb.org/t/p/w342/n8V09dDc02KsSN6Q4hC2BX6hN8X.jpg\",\n" +
            "            \"originalLanguage\": \"en\",\n" +
            "            \"genres\": [\n" +
            "                12,\n" +
            "                28,\n" +
            "                878\n" +
            "            ],\n" +
            "            \"cast\": [\n" +
            "                3,\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 12,\n" +
            "            \"title\": \"Finding Nemo\",\n" +
            "            \"tagline\": \"There are 3.7 trillion fish in the ocean. They're looking for one.\",\n" +
            "            \"overview\": \"Nemo, an adventurous young clownfish, is unexpectedly taken from his Great Barrier Reef home to a dentist's office aquarium. It's up to his worrisome father Marlin and a friendly but forgetful fish Dory to bring Nemo home -- meeting vegetarian sharks, surfer dude turtles, hypnotic jellyfish, hungry seagulls, and more along the way.\",\n" +
            "            \"popularity\": 22.619522,\n" +
            "            \"runtime\": 100,\n" +
            "            \"releaseDate\": \"2003-05-30\",\n" +
            "            \"revenue\": 940335536,\n" +
            "            \"budget\": 94000000,\n" +
            "            \"posterPath\": \"https://image.tmdb.org/t/p/w342/syPWyeeqzTQIxjIUaIFI7d0TyEY.jpg\",\n" +
            "            \"originalLanguage\": \"en\",\n" +
            "            \"genres\": [\n" +
            "                16,\n" +
            "                10751\n" +
            "            ],\n" +
            "            \"cast\": [\n" +
            "                2,\n" +
            "                3,\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";
    public static final String MOVIE_SEARCH = "{\n" +
            "    \"metadata\": {\n" +
            "        \"offset\": 1,\n" +
            "        \"limit\": 5,\n" +
            "        \"total\": 54\n" +
            "    },\n" +
            "    \"data\": [\n" +
            "        680,\n" +
            "        1893,\n" +
            "        12,\n" +
            "    ]\n" +
            "}";

    public static final String INVALID_ARTIST_INFO_GENRE = "{\n" +
            "    \"metadata\": {\n" +
            "        \"offset\": 0,\n" +
            "        \"limit\": 2,\n" +
            "        \"total\": 2\n" +
            "    },\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"name\": \"Harrison Ford\",\n" +
            "            \"gender\": 9999,\n" +
            "            \"profilePath\": \"https://image.tmdb.org/t/p/w185/7LOTdRfHU1H1qHBxpUv3jT04eWB.jpg\",\n" +
            "            \"movies\": [\n" +
            "                78,\n" +
            "                335984,\n" +
            "                140607,\n" +
            "                11\n" +
            "            ],\n" +
            "            \"id\": \"3\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
