package shorturl.shortener.dto;

public class ShortUrlResponse {

    private String shortUrl;

    private ShortUrlResponse(final String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public static ShortUrlResponse of(final String shortenUrl) {
        return new ShortUrlResponse(shortenUrl);
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
