package shorturl.shortener.acceptance;

import org.junit.jupiter.api.Test;

import shorturl.shortener.dto.ShortUrlRequest;
import shorturl.shortener.dto.ShortUrlResponse;

public class ShortUrlAcceptanceTest extends AcceptanceTest {

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
