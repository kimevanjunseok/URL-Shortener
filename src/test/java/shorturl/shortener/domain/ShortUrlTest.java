package shorturl.shortener.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import shorturl.shortener.exception.ShortUrlOutOfLengthException;

class ShortUrlTest {

    @DisplayName("constructor: 생성자 테스트")
    @Test
    void constructor() {
        // when then
        assertThat(new ShortUrl(1L, "https://www.naver.com/", "Aj4ndD")).isInstanceOf(ShortUrl.class);
    }

    @DisplayName("constructor: 생성시 shortUrl이 길이 8이상일 때 예외 처리")
    @Test
    void constructor_ShortUrlOutOfLength_Exception() {
        // when then
        assertThatThrownBy(() -> new ShortUrl(1L, "https://www.naver.com/", "7SAj4nZdD"))
                .isInstanceOf(ShortUrlOutOfLengthException.class)
                .hasMessageContaining("ShortUrl의 길이가 8을 초과하였습니다.");
    }
}