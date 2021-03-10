package shorturl.shortener.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import shorturl.shortener.domain.ShortUrl;
import shorturl.shortener.dto.OriginUrlResponse;
import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;
import shorturl.shortener.exception.URLNotFoundException;
import shorturl.shortener.repository.ShortUrlRepository;
import shorturl.shortener.utils.UrlEncoder;

@Import(value = {ShortUrlService.class, UrlEncoder.class})
class ShortUrlServiceTest extends ServiceTest {

    @Autowired
    private ShortUrlService shortUrlService;

    @Autowired
    private ShortUrlRepository shortUrlRepository;

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
        final ShortUrl shortUrl = shortUrlRepository.save(new ShortUrl("https://www.naver.com/", "B1Az9cZP", 1L));

        // when
        final OriginUrlResponse originUrlResponse = shortUrlService.findOriginUrlByShortUrl(shortUrl.getShortUrl());

        // than
        assertThat(originUrlResponse.getOriginUrl()).isEqualTo(shortUrl.getOriginUrl());
    }

    @DisplayName("findOriginUrlByShortUrl: URL에 대한 요청 수를 계산한다.")
    @Test
    void findOriginUrlByShortUrl_AddCount() {
        // given
        final ShortUrl shortUrl = shortUrlRepository.save(new ShortUrl("https://www.naver.com/", "B1Az9cZP", 1L));

        // when
        shortUrlService.findOriginUrlByShortUrl(shortUrl.getShortUrl());
        shortUrlService.findOriginUrlByShortUrl(shortUrl.getShortUrl());
        shortUrlService.findOriginUrlByShortUrl(shortUrl.getShortUrl());

        // than
        ShortUrl actual = shortUrlRepository.findByOriginUrl(shortUrl.getOriginUrl()).get();
        assertAll(
                () -> assertThat(actual.getOriginUrl()).isEqualTo(shortUrl.getOriginUrl()),
                () -> assertThat(actual.getRequestCount()).isEqualTo(4L)
        );
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