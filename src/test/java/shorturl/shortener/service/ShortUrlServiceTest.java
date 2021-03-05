package shorturl.shortener.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;

@Transactional
@SpringBootTest
class ShortUrlServiceTest {

    @Autowired
    private ShortUrlService shortUrlService;

    @DisplayName("create: url을 입력하면 짧게 만들어 준다.")
    @Test
    void create() {
        // given
        final ShortUrlRequest shortUrlRequest = new ShortUrlRequest("https://www.google.com/");

        // when
        final ShortUrlResponse shortUrlResponse = shortUrlService.create(shortUrlRequest);

        // than
        assertAll(
                () -> assertThat(shortUrlResponse.getOriginUrl()).isEqualTo("https://www.google.com/"),
                () -> assertThat(shortUrlResponse.getShortUrl()).isNotNull()
        );
    }

    @DisplayName("create: 기존에 만든 URL 입력 시 기존에 만든 짧은 URL를 출력한다.")
    @Test
    void create_SameUrl() {
        // given
        final ShortUrlRequest shortUrlRequest = new ShortUrlRequest("https://www.google.com");

        // when
        final ShortUrlResponse firstShortUrlResponse = shortUrlService.create(shortUrlRequest);
        final ShortUrlResponse secondShortUrlResponse = shortUrlService.create(shortUrlRequest);

        // than
        assertAll(
                () -> assertThat(firstShortUrlResponse.getOriginUrl()).isEqualTo("https://www.google.com"),
                () -> assertThat(secondShortUrlResponse.getOriginUrl()).isEqualTo("https://www.google.com"),
                () -> assertThat(firstShortUrlResponse.getShortUrl()).isEqualTo(secondShortUrlResponse.getShortUrl())
        );
    }
}