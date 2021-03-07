package shorturl.shortener.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import shorturl.shortener.domain.ShortUrl;
import shorturl.shortener.dto.ShortUrlResponse;
import shorturl.shortener.service.ShortUrlService;

@WebMvcTest(ShortUrlRedirectController.class)
class ShortUrlRedirectControllerTest {

    private static final String URL = "http://localhost:8080/";

    private MockMvc mockMvc;

    @MockBean
    private ShortUrlService shortUrlService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @DisplayName("shortUrl: 짧게 줄인 URL으로 요청을 보내면 원래 URL로 Redirect 한다.")
    @Test
    void shortUrl() throws Exception {
        final ShortUrlResponse shortUrlResponse = ShortUrlResponse.of(
                new ShortUrl("https://www.naver.com/", URL + "B1Az9c",1L));
        when(shortUrlService.findByShortUrl(any())).thenReturn(shortUrlResponse);

        this.mockMvc.perform(get("/{shortUrl}", "B1Az9c"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "https://www.naver.com/"))
                .andDo(print());
    }
}