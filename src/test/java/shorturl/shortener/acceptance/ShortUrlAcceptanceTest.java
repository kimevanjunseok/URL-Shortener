package shorturl.shortener.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShortUrlAcceptanceTest extends AcceptanceTest {

    @Test
    void shortUrl() {
        // Origin Url 요청에 대한 Short Url 생성
        final ShortUrlResponse firstShortUrlResponse =
                post(new ShortUrlRequest("https://www.naver.com/"), "/api/v1/short-url", ShortUrlResponse.class);

        assertAll(
                () -> assertThat(firstShortUrlResponse.getOriginUrl()).isEqualTo("https://www.naver.com/"),
                () -> assertThat(firstShortUrlResponse.getShortUrl()).isNotNull(),
                () -> assertThat(firstShortUrlResponse.getRequestCount()).isEqualTo(1L)
        );

        // 동일한 URL 요청해 대해 동일한 ShortUrl을 받고 request count가 올라간다.
        final ShortUrlResponse secondShortUrlResponse =
                post(new ShortUrlRequest("https://www.naver.com/"), "/api/v1/short-url", ShortUrlResponse.class);

        assertAll(
                () -> assertThat(secondShortUrlResponse.getOriginUrl()).isEqualTo("https://www.naver.com/"),
                () -> assertThat(secondShortUrlResponse.getShortUrl()).isEqualTo(firstShortUrlResponse.getShortUrl()),
                () -> assertThat(secondShortUrlResponse.getRequestCount()).isEqualTo(2L)
        );

        // short Url 요청에 대한 Origin Url로 Redirect
        final String[] splitUrl = secondShortUrlResponse.getShortUrl().split("/");
        final String shortUrl = splitUrl[splitUrl.length-1];

        redirect("/{shortUrl}", shortUrl);
    }
}
