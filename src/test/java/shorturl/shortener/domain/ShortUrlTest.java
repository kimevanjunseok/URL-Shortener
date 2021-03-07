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
        assertThat(new ShortUrl("https://www.naver.com/", "Aj4ndD", 1L)).isInstanceOf(ShortUrl.class);
    }

    @DisplayName("constructor: 생성시 shortUrl이 길이 8이상일 때 예외 처리")
    @Test
    void constructor_ShortUrlOutOfLength_Exception() {
        // when then
        assertThatThrownBy(() -> new ShortUrl("https://www.naver.com/", "7SAj4nZdD", 1L))
                .isInstanceOf(ShortUrlOutOfLengthException.class)
                .hasMessageContaining("ShortUrl의 길이가 8을 초과하였습니다.");
    }

    @DisplayName("updateShortUrl: shortUrl을 update 할 수 있다.")
    @Test
    void updateShortUrl() {
        // given
        final ShortUrl shortUrl = new ShortUrl("https://www.naver.com/", null, 1L);

        // when
        shortUrl.updateShortUrl("Aj4ndD");

        //then
        assertThat(shortUrl.getShortUrl()).isEqualTo("Aj4ndD");
    }

    @DisplayName("updateShortUrl: update시 shortUrl이 길이 8이상일 때 예외처리")
    @Test
    void updateShortUrl_ShortUrlOutOfLength_Exception() {
        // given
        final ShortUrl shortUrl = new ShortUrl("https://www.naver.com/", null, 1L);

        // when //then
        assertThatThrownBy(() -> shortUrl.updateShortUrl("7SAj4nZdD"))
                .isInstanceOf(ShortUrlOutOfLengthException.class)
                .hasMessageContaining("ShortUrl의 길이가 8을 초과하였습니다.");
    }

    @DisplayName("addRequestCount: 메서드 호출할 때마다 1씩 올라간다.")
    @Test
    void addRequestCount() {
        // given
        final ShortUrl shortUrl = new ShortUrl("https://www.naver.com/", null, 1L);

        // when
        shortUrl.addRequestCount();

        // then
        assertThat(shortUrl.getRequestCount()).isEqualTo(2L);
    }
}