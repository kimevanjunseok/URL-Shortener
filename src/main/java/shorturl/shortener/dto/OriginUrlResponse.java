package shorturl.shortener.dto;

import shorturl.shortener.domain.ShortUrl;

public class OriginUrlResponse {

    private final String originUrl;

    private OriginUrlResponse(final String originUrl) {
        this.originUrl = originUrl;
    }

    public static OriginUrlResponse of(final ShortUrl shortUrl) {
        return new OriginUrlResponse(shortUrl.getOriginUrl());
    }

    public String getOriginUrl() {
        return originUrl;
    }
}
