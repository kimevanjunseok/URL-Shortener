package shorturl.shortener.dto;

import shorturl.shortener.domain.ShortUrl;

public class ShortUrlResponse {

    private String originUrl;
    private String shortUrl;

    private ShortUrlResponse(final String originUrl, final String shortUrl) {
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }

    public static ShortUrlResponse of(final ShortUrl shortenUrl) {
        return new ShortUrlResponse(shortenUrl.getOriginUrl(), shortenUrl.getShortUrl());
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}
