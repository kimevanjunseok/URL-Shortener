package shorturl.shortener.acceptance;

import java.util.Objects;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;

public class ShortUrlAcceptanceTest extends AcceptanceTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @AfterEach
    void teardown() {
        Set<String> keys = redisTemplate.keys("*");
        for (final String key : Objects.requireNonNull(keys)) {
            redisTemplate.delete(key);
        }
    }

    @DisplayName("manageShortUrl: 사용자가 Url을 입력해서 ㄴShortUrl로 redirect까지 하는 과정")
    @Test
    void manageShortUrl() {
        // ShortUrl 생성
        final ShortUrlRequest shortUrlRequest = new ShortUrlRequest("https://www.naver.com");
        final ShortUrlResponse shortUrlResponse = post("/api/v1/short-url", shortUrlRequest, ShortUrlResponse.class);

        // ShortUrl Redirect
        final String[] splitUrl = shortUrlResponse.getShortUrl().split("/");
        final String shortUrl = splitUrl[splitUrl.length - 1];
        redirect("/" + shortUrl);
    }
}
