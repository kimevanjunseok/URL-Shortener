package shorturl.shortener.acceptance;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;

class ShortUrlAcceptanceTest extends AcceptanceTest {

    @Test
    void shortUrl() {
        // Origin Url 요청에 대한 Short Url 생성
        final ShortUrlResponse shortUrlResponse =
                post(new ShortUrlRequest("https://www.naver.com/"), "/api/v1/short-url", ShortUrlResponse.class);

        assertAll(
                () -> assertThat(shortUrlResponse.getOriginUrl()).isEqualTo("https://www.naver.com/"),
                () -> assertThat(shortUrlResponse.getShortUrl()).isNotNull(),
                () -> assertThat(shortUrlResponse.getRequestCount()).isEqualTo(1L)
        );

        // short Url 요청에 대한 Origin Url로 Redirect
        final String[] splitUrl = shortUrlResponse.getShortUrl().split("/");
        final String shortUrl = splitUrl[splitUrl.length-1];

        redirect("/{shortUrl}", shortUrl);
    }
}
