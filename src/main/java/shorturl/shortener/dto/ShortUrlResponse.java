package shorturl.shortener.dto;

import shorturl.shortener.domain.ShortUrl;

public class ShortUrlResponse {

    private final String originUrl;
    private final String shortUrl;
    private final long requestCount;

    private ShortUrlResponse(final String originUrl, final String shortUrl, final long requestCount) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.requestCount = requestCount;
    }

    public static ShortUrlResponse of(final ShortUrl shortenUrl) {
        return new ShortUrlResponse(shortenUrl.getOriginUrl(), shortenUrl.getShortUrl(), shortenUrl.getRequestCount());
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public long getRequestCount() {
        return requestCount;
    }
}
