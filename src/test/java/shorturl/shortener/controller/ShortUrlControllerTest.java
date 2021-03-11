package shorturl.shortener.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import shorturl.shortener.domain.ShortUrl;
import shorturl.shortener.dto.ShortUrlResponse;
import shorturl.shortener.service.ShortUrlService;

class ShortUrlControllerTest extends ControllerTest {

    @MockBean
    private ShortUrlService shortUrlService;

    @DisplayName("create: 기존 URL을 입력하면 짧게 만들어서 출력해준다.")
    @Test
    void create() throws Exception {
        final ShortUrlResponse shortUrlResponse = ShortUrlResponse.of(
                new ShortUrl(1L, "https://www.naver.com/", "B1Az9c"));
        when(shortUrlService.create(any())).thenReturn(shortUrlResponse);

        createByRequestBody("/api/v1/short-url", "{\"url\": \"https://www.naver.com/\"}",
                status().isCreated(), jsonPath("$.shortUrl", Is.is(shortUrlResponse.getShortUrl())));
    }

    @DisplayName("create: 빈 URL 요청이 올 때 예외 처리")
    @Test
    void create_ShortUrlRequest_NotBlankValidation_Exception() throws Exception {
        createByRequestBody("/api/v1/short-url", "{\"url\": \"\"}",
                status().isBadRequest(), jsonPath("$.message", Is.is("올바른 형식의 요청이 아닙니다.")));
    }

    @DisplayName("create: 잘못된 URL 형식의 요청이 올 때 예외 처리")
    @Test
    void create_ShortUrlRequest_URLValidation_Exception() throws Exception {
        createByRequestBody("/api/v1/short-url", "{\"url\": \"NotURL\"}",
                status().isBadRequest(), jsonPath("$.message", Is.is("올바른 형식의 요청이 아닙니다.")));
    }
}