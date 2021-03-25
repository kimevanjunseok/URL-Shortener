package shorturl.shortener.acceptance;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import shorturl.shortener.config.TestRedisConfiguration;

@SpringBootTest(
        classes = TestRedisConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public <T> T post(final String path, final Object request, Class<T> responseType) {
        return given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
        .when()
                .post(path)
        .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(responseType);
    }

    public void redirect(final String path) {
        given()
                .redirects().follow(false)
        .when()
                .get(path)
        .then()
                .log().all()
                .statusCode(HttpStatus.FOUND.value());
    }
}
