package shorturl.shortener.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import shorturl.shortener.domain.ShortUrl;
import shorturl.shortener.dto.OriginUrlResponse;
import shorturl.shortener.service.ShortUrlService;

class ShortUrlRedirectControllerTest extends ControllerTest {

    @MockBean
    private ShortUrlService shortUrlService;

    @DisplayName("main: main page 요청에 대한 응답")
    @Test
    void main() throws Exception {
        page("/", status().isOk(), content().string(containsString("Shortener")));
    }

    @DisplayName("shortUrl: 짧게 줄인 URL으로 요청을 보내면 원래 URL로 Redirect 한다.")
    @Test
    void shortUrl() throws Exception {
        final OriginUrlResponse originUrlResponse = OriginUrlResponse.of(
                new ShortUrl("https://www.naver.com/", "B1Az9c",1L));
        when(shortUrlService.findByShortUrl(any())).thenReturn(originUrlResponse);

        redirectByPathVariable("/{shortUrl}", "B1Az9c", header().string("Location", "https://www.naver.com/"));
    }
}