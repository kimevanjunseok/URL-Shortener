package shorturl.shortener.acceptance;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AcceptanceTest {

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    public <T> T post(final Object request, final String path, final Class<T> responseType) {
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

    public void redirect(final String path, final String shortUrl) {
        given()
                .redirects().follow(false)
        .when()
                .get(path, shortUrl)
        .then()
                .statusCode(HttpStatus.FOUND.value());
    }
}
