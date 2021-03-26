package shorturl.shortener.acceptance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;

public class ShortUrlAcceptanceTest extends AcceptanceTest {

    private static final String ORIGIN_URL = "OriginUrlKey";
    private static final String SHORT_URL = "ShortUrlKey";
    private static final String REQUEST_COUNT = "RequestCount";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @AfterEach
    void teardown() {
        redisTemplate.delete(ORIGIN_URL);
        redisTemplate.delete(SHORT_URL);
        redisTemplate.delete(REQUEST_COUNT);
    }

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
