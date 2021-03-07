package shorturl.shortener.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

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
                new ShortUrl("https://www.naver.com/", "B1Az9c",1L));
        when(shortUrlService.create(any())).thenReturn(shortUrlResponse);

        this.mockMvc.perform(post("/api/v1/short-url")
                .content("{\"url\": \"https://www.naver.com/\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortUrl", Is.is(shortUrlResponse.getShortUrl())))
                .andDo(print());
    }

    @DisplayName("create: 빈 URL 요청이 올 때 예외 처리")
    @Test
    void create_ShortUrlRequest_NotBlankValidation_Exception() throws Exception {
        this.mockMvc.perform(post("/api/v1/short-url")
                .content("{\"url\": \"\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("올바른 형식의 요청이 아닙니다.")))
                .andDo(print());
    }

    @DisplayName("create: 잘못된 URL 형식의 요청이 올 때 예외 처리")
    @Test
    void create_ShortUrlRequest_URLValidation_Exception() throws Exception {
        this.mockMvc.perform(post("/api/v1/short-url")
                .content("{\"url\": \"NotURL\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("올바른 형식의 요청이 아닙니다.")))
                .andDo(print());
    }
}