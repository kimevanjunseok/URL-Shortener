package shorturl.shortener.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import shorturl.shortener.dto.OriginUrlResponse;
import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;
import shorturl.shortener.exception.URLNotFoundException;

class ShortUrlServiceTest extends ServiceTest {

    @Autowired
    private ShortUrlService shortUrlService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @AfterEach
    void teardown() {
        Set<String> keys = redisTemplate.keys("*");
        for (final String key : Objects.requireNonNull(keys)) {
            redisTemplate.delete(key);
        }
    }

    @DisplayName("create: url을 입력하면 짧게 만들어 준다.")
    @Test
    void create() {
        // given
        final ShortUrlRequest shortUrlRequest = new ShortUrlRequest("https://www.google.com/");

        // when
        final ShortUrlResponse shortUrlResponse = shortUrlService.create(shortUrlRequest);

        // than
        assertAll(
                () -> assertThat(shortUrlResponse.getId()).isNotNull(),
                () -> assertThat(shortUrlResponse.getOriginUrl()).isEqualTo(shortUrlRequest.getUrl()),
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
                () -> assertThat(firstShortUrlResponse.getOriginUrl()).isEqualTo(shortUrlRequest.getUrl()),
                () -> assertThat(secondShortUrlResponse.getOriginUrl()).isEqualTo(shortUrlRequest.getUrl()),
                () -> assertThat(firstShortUrlResponse.getShortUrl()).isEqualTo(secondShortUrlResponse.getShortUrl())
        );
    }

    @DisplayName("findOriginUrlByShortUrl: 짧게 만든 URL을 입력하면 해당")
    @Test
    void findOriginUrlByShortUrl() {
        // given
        final ShortUrlRequest shortUrlRequest = new ShortUrlRequest("https://www.google.com");
        final ShortUrlResponse shortUrlResponse = shortUrlService.create(shortUrlRequest);
        final String[] splitUrl = shortUrlResponse.getShortUrl().split("/");
        final String shortUrl = splitUrl[splitUrl.length - 1];

        // when
        final OriginUrlResponse expect = shortUrlService.findOriginUrlByShortUrl(shortUrl);

        // than
        assertThat(expect.getOriginUrl()).isEqualTo(shortUrlResponse.getOriginUrl());
    }

    @DisplayName("findOriginUrlByShortUrl: 짧게 만든 URL이 존재하지 않을 때 예외 처리")
    @Test
    void findOriginUrlByShortUrl_URLNotFoundException() {
        // when then
        assertThatThrownBy(
                () -> shortUrlService.findOriginUrlByShortUrl("Am3Zd")
        ).isInstanceOf(URLNotFoundException.class)
        .hasMessageContaining("해당 URL은 존재하지 않습니다.");
    }
}