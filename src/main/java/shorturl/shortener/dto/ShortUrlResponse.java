package shorturl.shortener.dto;

import shorturl.shortener.domain.ShortUrl;

public class ShortUrlResponse {

    private final Long id;
    private final String originUrl;
    private final String shortUrl;

    private ShortUrlResponse(final Long id, final String originUrl, final String shortUrl) {
        this.id = id;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }

    public static ShortUrlResponse of(final ShortUrl shortUrl) {
        return new ShortUrlResponse(
                shortUrl.getId(), shortUrl.getOriginUrl(), shortUrl.getFullShortUrl());
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
}
