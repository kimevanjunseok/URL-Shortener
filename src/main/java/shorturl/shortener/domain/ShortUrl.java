package shorturl.shortener.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import shorturl.shortener.exception.ShortUrlOutOfLengthException;

public class ShortUrl implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String BASE_URL = "http://localhost:8080/";

    @Id
    private Long id;
    private String originUrl;
    private String shortUrl;

    public ShortUrl(final Long id, final String originUrl, final String shortUrl) {
        validateShortUrlLength(shortUrl);
        this.id = id;
        this.originUrl = originUrl;
        this.shortUrl = shortUrl;
    }

    private void validateShortUrlLength(final String shortenUrl) {
        if (shortenUrl != null && shortenUrl.length() > 8) {
            throw new ShortUrlOutOfLengthException("ShortUrl의 길이가 8을 초과하였습니다. length: " + shortenUrl.length());
        }
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

    public String getFullShortUrl() {
        return BASE_URL + shortUrl;
    }
}
