package shorturl.shortener.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
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
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Shortener")))
                .andDo(print());
    }

    @DisplayName("shortUrl: 짧게 줄인 URL으로 요청을 보내면 원래 URL로 Redirect 한다.")
    @Test
    void shortUrl() throws Exception {
        final OriginUrlResponse originUrlResponse = OriginUrlResponse.of(
                new ShortUrl("https://www.naver.com/", URL + "B1Az9c",1L));
        when(shortUrlService.findByShortUrl(any())).thenReturn(originUrlResponse);

        this.mockMvc.perform(get("/{shortUrl}", "B1Az9c"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "https://www.naver.com/"))
                .andDo(print());
    }
}