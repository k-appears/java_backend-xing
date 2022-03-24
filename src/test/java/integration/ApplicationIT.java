package integration;

import com.xing.challenge.movie.MovieController;
import com.xing.challenge.utils.ResponseError;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.xing.challenge.utils.JsonUtil.json;
import static com.xing.challenge.utils.JsonUtil.toJson;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static spark.Service.ignite;

class ApplicationIT {

    private static final Logger log = LoggerFactory.getLogger(ApplicationIT.class);

    private static final String PATH = "movies";

    private final HttpClient client = HttpClient.newBuilder().build();
    private final Service http = ignite();

    @BeforeEach
    void setup() {
        http.init();
        http.get("/movies", MovieController.fetchAllMovies, json());
        http.exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(toJson(new ResponseError(e)));
        });
        http.awaitInitialization();
        log.info("Spark started");
    }

    @AfterEach
    void shutdown() {
        log.info("Stopping Spark");
        http.stop();
    }

    private String performGet(String parameters) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:4567/" + PATH + parameters))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            fail(e.getMessage());
        }
        return response.body();
    }

    @Test
    void main_no_genre() {
        assertTrue(performGet("").contains("Empty genre"));
    }

    @Test
    void main_genreDrama_limit0() {
        String result = performGet("?genre=Drama&limit=0");
        assertTrue(result.contains("No ids available for limit 0") || result.contains("500"));
    }

    @Test
    void main_genreDrama_limit2_metadata() {
        assertTrue(performGet("?genre=Drama&limit=2").contains("\"metadata\":{\"offset\":0,\"limit\":2"));
    }

    @Test
    void main_genreAction_limit200_offset50_metadata() {
        String result = performGet("?genre=Action&limit=200&offset=5");
        assertTrue(result.contains("\"movies\":[]") || (result.contains("\"offset\":5") && result.contains("\"limit\":105")));
    }
}
