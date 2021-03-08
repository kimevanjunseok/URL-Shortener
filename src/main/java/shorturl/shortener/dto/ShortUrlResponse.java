package shorturl.shortener.dto;

import shorturl.shortener.domain.ShortUrl;

public class ShortUrlResponse {

    private final Long id;
    private final String originUrl;
    private final String shortUrl;
    private final long requestCount;

    private ShortUrlResponse(final Long id, final String originUrl, final String shortUrl, final long requestCount) {
        this.id = id;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
        this.requestCount = requestCount;
    }

    public static ShortUrlResponse of(final ShortUrl shortUrl) {
        return new ShortUrlResponse(
                shortUrl.getId(), shortUrl.getOriginUrl(), shortUrl.getFullShortUrl(), shortUrl.getRequestCount());
    }

    public Long getId() {
        return id;
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
