package shorturl.shortener.acceptance;

import static io.restassured.RestAssured.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;

import shorturl.shortener.config.TestRedisConfiguration;
import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;

@SpringBootTest(
        classes = TestRedisConfiguration.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortUrlAcceptanceTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void manageShortUrl() {
        // ShortUrl 생성
        final ShortUrlRequest shortUrlRequest = new ShortUrlRequest("https://www.naver.com");
        final ShortUrlResponse shortUrlResponse = given()
                .body(shortUrlRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/v1/short-url")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(ShortUrlResponse.class);

        // ShortUrl Redirect
        final String[] splitUrl = shortUrlResponse.getShortUrl().split("/");
        final String shortUrl = splitUrl[splitUrl.length - 1];
        given()
                .redirects().follow(false)
                .when()
                .get("/" + shortUrl)
                .then()
                .log().all()
                .statusCode(HttpStatus.FOUND.value());
    }
}
